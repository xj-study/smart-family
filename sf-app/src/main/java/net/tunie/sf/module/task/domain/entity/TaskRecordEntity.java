package net.tunie.sf.module.task.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("t_task_record")
@Data
public class TaskRecordEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long taskId;

    private Long userId;

    private LocalDate taskDate;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
