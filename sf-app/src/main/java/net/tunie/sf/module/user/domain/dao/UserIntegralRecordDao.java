package net.tunie.sf.module.user.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.user.domain.entity.UserIntegralRecordEntity;
import net.tunie.sf.module.user.domain.vo.UserIntegralRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserIntegralRecordDao extends BaseMapper<UserIntegralRecordEntity> {
    @Select("SELECT IR.*, R.content, R.ref_type FROM t_user_integral_record as IR " +
            "LEFT JOIN t_record as R on IR.ref_id = R.id " +
            "WHERE IR.user_id = #{userId} " +
            "ORDER BY IR.update_time DESC")
    List<UserIntegralRecordVo> selectRecords(@Param("userId") Long userId);
}
