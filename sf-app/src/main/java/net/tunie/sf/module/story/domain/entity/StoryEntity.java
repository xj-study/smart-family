package net.tunie.sf.module.story.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

@TableName("t_story")
@Data
public class StoryEntity extends BaseEntity {

    private Long userId;

    private String title;

    private String content;

    private Integer status;

    private Integer costAmount;

    private Integer costType;

    private Integer type;

}
