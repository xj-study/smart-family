package net.tunie.sf.module.user.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.user.domain.entity.UserIntegralRecordEntity;
import net.tunie.sf.module.user.domain.vo.UserIntegralRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserIntegralRecordDao extends BaseMapper<UserIntegralRecordEntity> {
    List<UserIntegralRecordVo> selectRecords(@Param("userId") Long userId);
}
