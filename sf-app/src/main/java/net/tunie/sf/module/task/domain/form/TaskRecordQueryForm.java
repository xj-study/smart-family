package net.tunie.sf.module.task.domain.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRecordQueryForm {

    private Long id;

    private Integer status;
}
