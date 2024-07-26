package net.tunie.sf.module.ques.domain.form;

import lombok.Data;

@Data
public class QuesQueryForm {

    private Long id;

    private Integer type;

    private String rules;
}
