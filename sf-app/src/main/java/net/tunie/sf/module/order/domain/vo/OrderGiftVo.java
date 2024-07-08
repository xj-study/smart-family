package net.tunie.sf.module.order.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderGiftVo {
    private Long id;

    private Long userId;

    private Long giftId;

    private Integer status;

    private Integer num;

    private String name;

    private String content;

    private Integer price;

    private Integer totalPrice;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
