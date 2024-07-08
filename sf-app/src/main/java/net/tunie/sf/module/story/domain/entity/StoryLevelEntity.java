package net.tunie.sf.module.story.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

import java.time.LocalDate;

@TableName("t_story_level")
@Data
public class StoryLevelEntity extends BaseEntity {

    private Integer order;

    private Long storyId;

    private String title;

    private String content;

    private Integer prize;

    private Integer refType;

    private String refRules;
}
