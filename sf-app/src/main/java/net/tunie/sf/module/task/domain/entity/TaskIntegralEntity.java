package net.tunie.sf.module.task.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BasicEntity;

import java.time.LocalDateTime;

@Data
@TableName("t_task_integral")
public class TaskIntegralEntity extends BasicEntity {
    @TableId()
    private Long taskId;

    private Integer integral;
}
