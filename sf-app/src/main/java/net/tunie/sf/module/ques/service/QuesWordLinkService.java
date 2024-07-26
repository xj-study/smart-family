package net.tunie.sf.module.ques.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.tunie.sf.module.ques.domain.dao.QuesWordLinkDao;
import net.tunie.sf.module.ques.domain.entity.QuesWordLinkEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuesWordLinkService extends ServiceImpl<QuesWordLinkDao, QuesWordLinkEntity> {

    public void update(QuesWordLinkEntity quesWordLinkEntity) {
        QuesWordLinkEntity entity = this.getOne(Wrappers.lambdaQuery(QuesWordLinkEntity.class)
                .eq(QuesWordLinkEntity::getQuesWordId, quesWordLinkEntity.getQuesWordId())
                .eq(QuesWordLinkEntity::getSort, quesWordLinkEntity.getSort())
                .eq(QuesWordLinkEntity::getQuesId, quesWordLinkEntity.getQuesId()));

        if (entity == null) {
            this.save(quesWordLinkEntity);
        }
    }

    public void removeByQuesId(Long quesId) {
        List<QuesWordLinkEntity> quesWordLinkEntities = this.list(Wrappers.lambdaQuery(QuesWordLinkEntity.class).eq(QuesWordLinkEntity::getQuesId, quesId));
        this.removeBatchByIds(quesWordLinkEntities.stream().map(QuesWordLinkEntity::getId).toList());
    }
}
