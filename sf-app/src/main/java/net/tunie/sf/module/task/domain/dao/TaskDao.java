package net.tunie.sf.module.task.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.task.domain.entity.TaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TaskDao extends BaseMapper<TaskEntity> {
    void updateDisableFlag(@Param("id") Long id, @Param("disableFlag") boolean disableFlag);
}
