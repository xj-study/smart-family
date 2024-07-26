package net.tunie.sf.module.ques.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.ques.domain.dao.QuesDao;
import net.tunie.sf.module.ques.domain.entity.QuesEntity;
import net.tunie.sf.module.ques.domain.entity.QuesWordAnswerEntity;
import net.tunie.sf.module.ques.domain.entity.QuesWordEntity;
import net.tunie.sf.module.ques.domain.entity.QuesWordLinkEntity;
import net.tunie.sf.module.ques.domain.form.QuesAddForm;
import net.tunie.sf.module.ques.domain.form.QuesQueryForm;
import net.tunie.sf.module.ques.domain.form.QuesSubmitForm;
import net.tunie.sf.module.ques.domain.form.QuesWordQueryForm;
import net.tunie.sf.module.ques.domain.vo.QuesVo;
import net.tunie.sf.module.ques.domain.vo.QuesWordVo;
import net.tunie.sf.module.word.domain.entity.WordEntity;
import net.tunie.sf.module.word.service.WordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuesService extends ServiceImpl<QuesDao, QuesEntity> {

    @Resource
    private QuesWordService quesWordService;

    @Resource
    private QuesWordLinkService quesWordLinkService;

    @Resource
    private WordService wordService;

    @Resource
    private QuesWordAnswerService quesWordAnswerService;

    public void addOrUpdateQues(QuesAddForm quesAddForm) {
        // 1. 取得题目规则
        JSONObject quesRules = quesAddForm.getRules();
        if (quesRules == null) {
            return;
        }

        QuesEntity quesEntity = this.getByRefTypeAndRefId(quesAddForm.getType(), quesAddForm.getId());
        if (quesEntity == null) {
            // 数据记录不存在
            quesEntity = new QuesEntity(quesAddForm.getType(), quesAddForm.getId());
            this.save(quesEntity);
        } else {
            // 存在记录，则需要删除与之关联的所有记录
            quesWordLinkService.removeByQuesId(quesEntity.getId());
        }

        // 目前只有单词的配置规则
        List<QuesWordEntity> list = this.parseRules(quesRules);
        //ques word 关联一下 ques 方便后续快速查询
        for (int i = 0; i < list.size(); i++) {
            QuesWordLinkEntity quesWordLinkEntity = new QuesWordLinkEntity();
            quesWordLinkEntity.setQuesId(quesEntity.getId());
            quesWordLinkEntity.setQuesWordId(list.get(i).getId());
            quesWordLinkEntity.setSort(i);
            this.quesWordLinkService.update(quesWordLinkEntity);
        }

    }

    public ResponseDTO<QuesVo> queryQues(QuesQueryForm quesQueryForm) {

        QuesEntity quesEntity = this.getByRefTypeAndRefId(quesQueryForm.getType(), quesQueryForm.getId());
        if (quesEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        // 取得所有题目
        List<QuesWordEntity> list = quesWordService.listQuesWord(quesEntity.getId());

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

    public ResponseDTO<Boolean> submitQues(QuesSubmitForm quesSubmitForm) {
        List<QuesWordAnswerEntity> quesWordAnswerEntities = SmartBeanUtil.copyList(quesSubmitForm.getAnswers(), QuesWordAnswerEntity.class);
        this.quesWordAnswerService.saveBatch(quesWordAnswerEntities);
        boolean result = quesWordAnswerEntities.stream().noneMatch(QuesWordAnswerEntity::getWrongFlag);
        return ResponseDTO.ok(result);
    }
}
