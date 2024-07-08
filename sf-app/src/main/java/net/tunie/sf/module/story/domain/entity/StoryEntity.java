package net.tunie.sf.module.story.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

import java.time.LocalDate;

@TableName("t_story")
@Data
public class StoryEntity extends BaseEntity {

    private Long userId;

    private String title;

    private String content;

    private Integer costAmount;

    private Integer costType;

}
