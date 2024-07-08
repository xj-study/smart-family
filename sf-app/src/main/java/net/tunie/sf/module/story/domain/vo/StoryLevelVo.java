package net.tunie.sf.module.story.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StoryLevelVo {

    private Long id;

    private Integer order;

    private String title;

    private String content;

    private Integer refType;

    private String refRules;

    private LocalDate createTime;
}
