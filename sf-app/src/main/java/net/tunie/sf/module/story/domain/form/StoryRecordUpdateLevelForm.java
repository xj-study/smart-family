package net.tunie.sf.module.story.domain.form;

import lombok.Data;

@Data
public class StoryRecordUpdateLevelForm {
    private Long id;
    private Long userId;
    private Long levelId;
    private String storyTitle;
}
