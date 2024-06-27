package net.tunie.sf.module.task.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_task_integral")
public class TaskIntegralEntity {
    @TableId()
    private Long taskId;

    private Integer integral;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
