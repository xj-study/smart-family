package net.tunie.sf.module.gift.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GiftVo {

    private Long id;

    private String name;

    private String content;

    private Double price;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
