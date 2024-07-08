package net.tunie.sf.module.order.domain.vo;

import lombok.Data;

@Data
public class OrderGiftJsonVo {
    private Long id;

    private Integer num;

    private String name;

    private String content;

    private Integer price;

    private Integer totalPrice;
}
