package net.tunie.sf.module.story.domain.form;

import lombok.Data;

@Data
public class StoryLevelAddForm {

    private Integer levelOrder;

    private Long storyId;

    private String title;

    private String content;

    private Integer prize;

    private Integer refType;

    private String refRules;
}
