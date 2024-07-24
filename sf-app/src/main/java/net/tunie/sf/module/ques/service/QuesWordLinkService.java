package net.tunie.sf.module.ques.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.tunie.sf.module.ques.domain.dao.QuesWordLinkDao;
import net.tunie.sf.module.ques.domain.entity.QuesWordLinkEntity;
import org.springframework.stereotype.Service;

@Service
public class QuesWordLinkService extends ServiceImpl<QuesWordLinkDao, QuesWordLinkEntity> {

    public void update(QuesWordLinkEntity quesWordLinkEntity) {
        QuesWordLinkEntity entity = this.getOne(Wrappers.lambdaQuery(QuesWordLinkEntity.class)
                .eq(QuesWordLinkEntity::getQuesWordId, quesWordLinkEntity.getQuesWordId())
                .eq(QuesWordLinkEntity::getQuesId, quesWordLinkEntity.getQuesId()));
        if (entity == null) {
            this.save(quesWordLinkEntity);
        }
    }
}
