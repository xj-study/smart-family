package net.tunie.sf.module.task.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.task.domain.entity.TaskEntity;
import net.tunie.sf.module.task.domain.vo.TaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TaskDao extends BaseMapper<TaskEntity> {
    void updateDisableFlag(@Param("id") Long id, @Param("disableFlag") boolean disableFlag);

    List<TaskVo> queryTask(@Param("userId") Long userId,  @Param("disableFlag") boolean disableFlag);
}
