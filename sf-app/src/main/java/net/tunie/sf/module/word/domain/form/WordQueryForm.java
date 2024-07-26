package net.tunie.sf.module.word.domain.form;

import lombok.Data;
import net.tunie.sf.common.domain.PageParam;

@Data
public class WordQueryForm extends PageParam {
    private String keyword;

    private Integer level;
}
