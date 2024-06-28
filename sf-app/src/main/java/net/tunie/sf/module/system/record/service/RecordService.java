package net.tunie.sf.module.system.record.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.system.record.domain.dao.RecordDao;
import net.tunie.sf.module.system.record.domain.entity.RecordEntity;
import net.tunie.sf.module.system.record.domain.form.RecordAddForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    @Resource
    private RecordDao recordDao;

    public List<RecordEntity> query(Long userId) {
        QueryWrapper<RecordEntity> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id", userId);
        return recordDao.selectList(queryWrapper);
    }

    public Long add(RecordAddForm recordAddForm) {
        RecordEntity recordEntity = SmartBeanUtil.copy(recordAddForm, RecordEntity.class);
        recordDao.insert(recordEntity);
        return recordEntity.getId();
    }
}
