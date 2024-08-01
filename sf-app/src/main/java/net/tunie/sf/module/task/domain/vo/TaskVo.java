package net.tunie.sf.module.task.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    private List<Long> tag;

    private String tagStr;
}