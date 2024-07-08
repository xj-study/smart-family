package net.tunie.sf.module.story.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@TableName("t_story_level")
@Data
public class StoryLevelEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private Integer order;

    private Long storyId;

    private String title;

    private String content;

    private Integer prize;

    private Integer refType;

    private String refRules;

    private Boolean disableFlag;

    private LocalDate updateTime;

    private LocalDate createTime;
}
