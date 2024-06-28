package net.tunie.sf.module.order.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.module.order.domain.form.OrderGiftAddForm;
import net.tunie.sf.module.order.service.OrderGiftService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderGiftController {

    @Resource
    private OrderGiftService orderGiftService;

    @PostMapping("/order/gift/buy")
    private ResponseDTO<Long> createOrder(@RequestBody OrderGiftAddForm orderGiftAddForm) {
        orderGiftAddForm.setUserId(SmartRequestUtil.getRequestUserId());
        return orderGiftService.createOrder(orderGiftAddForm);
    }
}
