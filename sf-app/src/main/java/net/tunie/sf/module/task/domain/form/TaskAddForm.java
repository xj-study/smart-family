package net.tunie.sf.module.task.domain.form;

import lombok.Data;

@Data
public class TaskAddForm {

    private Long userId;

    private String title;

    private String content;

    private String rules;

    private Boolean verifyFlag;

    private Boolean disableFlag;

    private Integer taskType;

    private Integer integral;
}
