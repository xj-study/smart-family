package net.tunie.sf.module.ques.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.service.RulesService;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.ques.domain.dao.QuesDao;
import net.tunie.sf.module.ques.domain.entity.QuesEntity;
import net.tunie.sf.module.ques.domain.entity.QuesWordEntity;
import net.tunie.sf.module.ques.domain.entity.QuesWordLinkEntity;
import net.tunie.sf.module.ques.domain.form.QuesQueryForm;
import net.tunie.sf.module.ques.domain.form.QuesWordQueryForm;
import net.tunie.sf.module.ques.domain.vo.QuesVo;
import net.tunie.sf.module.ques.domain.vo.QuesWordVo;
import net.tunie.sf.module.ques.utils.QuesRulesFactory;
import net.tunie.sf.module.word.domain.entity.WordEntity;
import net.tunie.sf.module.word.service.WordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuesService extends ServiceImpl<QuesDao, QuesEntity> {

    @Resource
    private QuesRulesFactory quesRulesFactory;

    @Resource
    private QuesWordService quesWordService;

    @Resource
    private QuesWordLinkService quesWordLinkService;

    @Resource
    private WordService wordService;


    public ResponseDTO<QuesVo> queryQues(QuesQueryForm quesQueryForm) {
        // 1. 取得题目规则
        JSONObject quesRules = this.getQuesRules(quesQueryForm.getType(), quesQueryForm.getId());
        if (quesRules == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        // 2. 取得题目记录，若不存在，则创建和条记录
        QuesEntity quesEntity = this.getByRefTypeAndRefId(quesQueryForm.getType(), quesQueryForm.getId());
        List<QuesWordEntity> list = null;
        if (quesEntity == null) {
            // 数据记录不存在
            quesEntity = new QuesEntity(quesQueryForm.getType(), quesQueryForm.getId());
            this.save(quesEntity);
            // 3.1 根据规则，生成对应的题目数据
            // 目前只有单词的配置规则
            list = this.parseRules(quesRules);
            //ques word 关联一下 ques 方便后续快速查询
            Long quesEntityId = quesEntity.getId();
            list.forEach(item -> {
                QuesWordLinkEntity quesWordLinkEntity = new QuesWordLinkEntity();
                quesWordLinkEntity.setQuesId(quesEntityId);
                quesWordLinkEntity.setQuesWordId(item.getId());
                this.quesWordLinkService.update(quesWordLinkEntity);
            });

        } else {
            // 已经存在，则直接取数据记录
            // 3.2 根据题目 id，取得关联的单词题目
            list = quesWordService.listQuesWord(quesEntity.getId());
        }

        // 返回数据
        QuesVo quesVo = new QuesVo();
        quesVo.setId(quesEntity.getId());

        List<QuesWordVo> quesWordVos = SmartBeanUtil.copyList(list, QuesWordVo.class);
        quesWordVos.forEach(this.quesWordService::parseQuesWord);
        quesVo.setList(quesWordVos);
        return ResponseDTO.ok(quesVo);
    }

    private List<QuesWordEntity> parseRules(JSONObject quesRules) {
        List<QuesWordEntity> list = new ArrayList<>();
        JSONArray jsonArray = quesRules.getJSONArray("list");
        jsonArray.forEach(rule -> {
            JSONObject jsonObject = (JSONObject) rule;
            Integer num = jsonObject.getInteger("num");
            Integer level = jsonObject.getInteger("level");
            Integer quesType = jsonObject.getInteger("quesType");
            Integer selectNum = jsonObject.getInteger("selectNum");
            if (selectNum == null) {
                selectNum = 2;
            }
            Integer fillLevel = jsonObject.getInteger("fillLevel");
            if (fillLevel == null) {
                fillLevel = 1;
            }
            for (int i = 0; i < num; i++) {
                // 随机 quesWord
                QuesWordQueryForm quesWordQueryForm = new QuesWordQueryForm();
                WordEntity entity = this.wordService.randomWordByLevel(level);
                quesWordQueryForm.setWordId(entity.getId());
                quesWordQueryForm.setType(quesType);
                quesWordQueryForm.setSelectNum(selectNum);
                quesWordQueryForm.setFillLevel(fillLevel);
                QuesWordEntity one = this.quesWordService.getOneOrSave(quesWordQueryForm);

                list.add(one);
            }
        });

        return list;
    }

    private QuesEntity getByRefTypeAndRefId(Integer refType, Long refId) {
        return this.getOne(Wrappers.lambdaQuery(QuesEntity.class)
                .eq(QuesEntity::getRefType, refType)
                .eq(QuesEntity::getRefId, refId));
    }

    private JSONObject getQuesRules(Integer refType, Long refId) {
        RulesService rulesService = quesRulesFactory.getService(refType);
        if (rulesService == null) {
            return null;
        }
        return rulesService.getRules(refId);
    }
}
