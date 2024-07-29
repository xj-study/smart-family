package net.tunie.sf.module.story.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class StoryRecordVo {

    private Long id;

    private String title;

    private String content;

    private Integer costAmount;

    private Integer costType;

    private Integer status;

    private Integer type;

    private Long storyId;

    private Integer passCount;

    private Long levelId;

    private Integer levelIndex;

    private List<StoryLevelVo> levels;
}
