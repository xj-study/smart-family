package net.tunie.sf.module.task.domain.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskRecordVo {
    private Long id;

    private Long taskId;

    private String title;

    private String content;

    private String rules;

    private LocalDate taskDate;

    private Integer status;

    private Integer taskType;

    private Integer integral;

    private List<Long> tag;

    private String tagStr;

}
