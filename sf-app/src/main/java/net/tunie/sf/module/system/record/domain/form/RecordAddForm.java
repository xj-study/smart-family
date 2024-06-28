package net.tunie.sf.module.system.record.domain.form;

import lombok.Data;

@Data
public class RecordAddForm {

    private Long refId;

    private Integer refType;

    private Long userId;

    private String content;
}
