package net.tunie.sf.module.task.domain.form;

import lombok.Data;

@Data
public class TaskRecordQueryForm {

    private Long id;

    private Integer taskType;

    private Integer status;

    private Integer date;

    private String keyword;

}
