package net.tunie.sf.module.user.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.annotation.Resource;
import lombok.Data;
import net.tunie.sf.module.user.domain.entity.UserIntegralEntity;
import net.tunie.sf.module.user.domain.entity.UserIntegralRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserIntegralRecordDao extends BaseMapper<UserIntegralRecordEntity> {

}
