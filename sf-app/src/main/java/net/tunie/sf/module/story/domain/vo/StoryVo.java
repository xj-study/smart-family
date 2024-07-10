package net.tunie.sf.module.story.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StoryVo {

    private Long id;

    private String title;

    private String content;

    private Integer costAmount;

    private Integer costType;

    private Integer status;

    private LocalDateTime createTime;
}
