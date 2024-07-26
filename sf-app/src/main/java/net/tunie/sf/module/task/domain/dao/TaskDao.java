package net.tunie.sf.module.task.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.task.domain.entity.TaskEntity;
import net.tunie.sf.module.task.domain.vo.TaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TaskDao extends BaseMapper<TaskEntity> {

    @Select("SELECT * FROM t_task T LEFT JOIN t_task_integral I ON T.id = I.task_id WHERE T.user_id = #{userId} order by T.create_time")
    List<TaskVo> getTaskList(@Param("userId") Long requestUserId);
}
