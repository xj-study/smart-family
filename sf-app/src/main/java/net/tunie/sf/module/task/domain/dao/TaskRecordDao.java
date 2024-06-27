package net.tunie.sf.module.task.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.task.domain.entity.TaskRecordEntity;
import net.tunie.sf.module.task.domain.vo.TaskRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Component
public interface TaskRecordDao extends BaseMapper<TaskRecordEntity> {

    List<TaskRecordVo> queryDailyTaskRecord(@Param("taskUserId") Long taskUserId, @Param("recordUserId") Long recordUserId, @Param("taskDate") LocalDate taskDate, @Param("status") Integer status);
}
