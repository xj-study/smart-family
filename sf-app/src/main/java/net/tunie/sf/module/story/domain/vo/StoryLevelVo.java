package net.tunie.sf.module.story.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;
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

    // 状态 true(通关)
    private Boolean pass;
}
