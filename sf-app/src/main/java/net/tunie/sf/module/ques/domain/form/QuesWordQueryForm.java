package net.tunie.sf.module.ques.domain.form;

import lombok.Data;

@Data
public class QuesWordQueryForm {
    private Long wordId;

    private Integer type;

    private Integer fillLevel;

    private Integer selectNum;
}
