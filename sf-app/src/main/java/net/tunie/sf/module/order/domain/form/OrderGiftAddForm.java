package net.tunie.sf.module.order.domain.form;

import lombok.Data;

@Data
public class OrderGiftAddForm {

    private Long userId;

    private Long giftId;

    private Integer num;


}
