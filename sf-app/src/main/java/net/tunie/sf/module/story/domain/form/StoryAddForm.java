package net.tunie.sf.module.story.domain.form;

import lombok.Data;

@Data
public class StoryAddForm {

    private Long userId;

    private String title;

    private String content;

    private Integer costAmount;

    private Integer costType;
}
