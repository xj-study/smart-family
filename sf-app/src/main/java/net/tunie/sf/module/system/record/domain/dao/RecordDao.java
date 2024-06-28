package net.tunie.sf.module.system.record.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import net.tunie.sf.module.system.record.domain.entity.RecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RecordDao extends BaseMapper<RecordEntity> {
}
