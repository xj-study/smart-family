package net.tunie.sf.module.story.domain.vo;

import lombok.Data;
import net.tunie.sf.module.story.constant.StoryLevelStatusConst;

import java.time.LocalDateTime;

@Data
public class StoryLevelVo {

    private Long id;

    private Integer levelOrder;

    private Long storyId;

    private String title;

    private String content;

    private Integer prize;

    private Integer refType;

    private String refRules;

    private LocalDateTime createTime;

    private Integer status = StoryLevelStatusConst.LOCK;

}
