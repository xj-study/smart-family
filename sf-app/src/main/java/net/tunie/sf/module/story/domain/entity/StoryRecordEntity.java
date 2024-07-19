package net.tunie.sf.module.story.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

@Data
@TableName("t_story_record")
public class StoryRecordEntity extends BaseEntity {

    private Long userId;
    private Long storyId;
    private Long levelId;
    private Integer levelIndex;
    private Integer status;
    private Integer passCount;


}
