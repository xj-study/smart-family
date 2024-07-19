package net.tunie.sf.module.task.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

import java.time.LocalDateTime;

@TableName("t_task")
@Data
public class TaskEntity extends BaseEntity {

    private Long userId;

    private Integer taskType;

    private String title;

    private String content;

    private String rules;

    private Boolean verifyFlag;
}
