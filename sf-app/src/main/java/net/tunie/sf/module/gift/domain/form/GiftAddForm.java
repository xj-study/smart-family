package net.tunie.sf.module.gift.domain.form;

import lombok.Data;

@Data
public class GiftAddForm {

    private Long userId;

    private String name;

    private String content;

    private Double price;
}
