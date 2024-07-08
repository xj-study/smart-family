package net.tunie.sf.module.system.record.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.system.record.domain.dao.RecordDao;
import net.tunie.sf.module.system.record.domain.entity.RecordEntity;
import net.tunie.sf.module.system.record.domain.form.RecordAddForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService extends ServiceImpl<RecordDao, RecordEntity> {

    public List<RecordEntity> query(Long userId) {
        LambdaQueryWrapper<RecordEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RecordEntity::getUserId, userId);
        return this.list(lambdaQueryWrapper);
    }

    public Long add(RecordAddForm recordAddForm) {
        RecordEntity recordEntity = SmartBeanUtil.copy(recordAddForm, RecordEntity.class);
        this.save(recordEntity);
        return recordEntity.getId();
    }
}
