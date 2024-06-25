package net.tunie.sf.module.gift.domain.form;

import lombok.Data;

@Data
public class GiftUpdateForm extends GiftAddForm {
    private Long giftId;
}