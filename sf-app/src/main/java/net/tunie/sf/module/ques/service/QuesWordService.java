package net.tunie.sf.module.ques.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.ques.domain.dao.QuesWordDao;
import net.tunie.sf.module.ques.domain.entity.QuesWordEntity;
import net.tunie.sf.module.ques.domain.form.QuesWordQueryForm;
import net.tunie.sf.module.ques.domain.vo.QuesWordVo;
import net.tunie.sf.module.ques.utils.QuesWordUtils;
import net.tunie.sf.module.word.domain.entity.WordEntity;
import net.tunie.sf.module.word.domain.vo.WordVo;
import net.tunie.sf.module.word.service.WordService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class QuesWordService extends ServiceImpl<QuesWordDao, QuesWordEntity> {

    @Resource
    private WordService wordService;

    public List<QuesWordEntity> listQuesWord(Long quesId) {
        List<QuesWordEntity> list = this.baseMapper.list(quesId);

        // 如果题目类型是选择题，则需要处理选项问题
        list.forEach(this::updateOptions);

        return list;
    }

    public void parseQuesWord(QuesWordVo quesWordVo) {
        if (quesWordVo.getEnValue() != null) return;
        WordEntity wordEntity = this.wordService.getById(quesWordVo.getWordId());
        quesWordVo.setEnValue(wordEntity.getEnValue());
        quesWordVo.setZhValue(wordEntity.getZhValue());
    }

    public void updateOptions(QuesWordEntity quesWordEntity) {
        if (QuesWordUtils.checkNeedOptions(quesWordEntity)) {

            if (quesWordEntity.getOptions() == null || QuesWordUtils.checkNeedRefreshOptions(quesWordEntity)) {
                // 需要处理选项
                List<WordEntity> wordEntities = wordService.randomWordList(quesWordEntity.getSelectNum());
                List<Long> list = wordEntities.stream().map(WordEntity::getId).toList();
                String join = Strings.join(list, ',');
                quesWordEntity.setOptions(join);
                quesWordEntity.setUpdateTime(LocalDateTime.now());
                // 更新一下
                this.updateById(quesWordEntity);
                quesWordEntity.setOptionList(SmartBeanUtil.copyList(wordEntities, WordVo.class));
            } else if (quesWordEntity.getOptionList() == null) {
                List<Long> list = Arrays.stream(quesWordEntity.getOptions().split(",")).map(Long::valueOf).toList();
                List<WordEntity> wordEntities = wordService.listByIds(list);
                quesWordEntity.setOptionList(SmartBeanUtil.copyList(wordEntities, WordVo.class));
            }

        }
    }

    /**
     * 若查找不到，就插入一条新的数据并返回这条数据
     */
    public QuesWordEntity getOneOrSave(QuesWordQueryForm quesWordQueryForm) {
        QuesWordEntity entity = this.getOne(Wrappers.lambdaQuery(QuesWordEntity.class)
                .eq(QuesWordEntity::getWordId, quesWordQueryForm.getWordId())
                .eq(QuesWordEntity::getType, quesWordQueryForm.getType())
                .eq(QuesWordEntity::getSelectNum, quesWordQueryForm.getSelectNum())
                .eq(QuesWordEntity::getFillLevel, quesWordQueryForm.getFillLevel())
        );
        if (entity == null) {
            entity = SmartBeanUtil.copy(quesWordQueryForm, QuesWordEntity.class);
            this.save(entity);
        }

        this.updateOptions(entity);

        return entity;
    }

}
