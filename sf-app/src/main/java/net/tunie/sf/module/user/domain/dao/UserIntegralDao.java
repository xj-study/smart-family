package net.tunie.sf.module.user.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.annotation.Resource;
import lombok.Data;
import net.tunie.sf.module.user.domain.entity.UserIntegralEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserIntegralDao extends BaseMapper<UserIntegralEntity> {

}
