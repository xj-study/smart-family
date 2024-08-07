package net.tunie.sf.module.task.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskVo {
    private Long id;

    private Long taskId;

    private String title;

    private String content;

    private String rules;

    private LocalDateTime createTime;

    private Boolean verifyFlag;

    private Integer taskType;

    private Integer integral;
}