package net.tunie.sf.module.story.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryVo {

    private Long id;

    private String title;

    private String content;

    private Integer costAmount;

    private Integer costType;

    private Integer status;

    private Integer type;

    private LocalDateTime createTime;
}
