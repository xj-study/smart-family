package net.tunie.sf.module.task.domain.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRecordCompleteForm {

    private Long taskId;

    private Long userId;

    private LocalDate taskDate;

    private Integer status;
}
