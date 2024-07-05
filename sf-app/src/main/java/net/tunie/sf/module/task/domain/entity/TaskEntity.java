package net.tunie.sf.module.task.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("t_task")
@Data
public class TaskEntity {

    @TableId(type = IdType.AUTO)
    private Long taskId;

    private Long userId;

    private Integer taskType;

    private String title;

    private String content;

    private Boolean verifyFlag;

    private Boolean disableFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
