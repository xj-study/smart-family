package net.tunie.sf.module.order.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.module.order.domain.form.OrderGiftAddForm;
import net.tunie.sf.module.order.domain.form.OrderGiftQueryForm;
import net.tunie.sf.module.order.domain.vo.OrderGiftVo;
import net.tunie.sf.module.order.service.OrderGiftService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderGiftController {

    @Resource
    private OrderGiftService orderGiftService;

    @PostMapping("/order/gift/buy")
    public ResponseDTO<Long> createOrder(@RequestBody OrderGiftAddForm orderGiftAddForm) {
        orderGiftAddForm.setUserId(SmartRequestUtil.getRequestUserId());
        return orderGiftService.createOrder(orderGiftAddForm);
    }

    @GetMapping("/order/gift/{orderId}/{status}/update")
    public ResponseDTO<String> updateOrderStatus(@PathVariable Long orderId, @PathVariable Integer status) {
        return orderGiftService.updateOrderStatus(orderId, status);
    }

    @PostMapping("/order/gift/query")
    public ResponseDTO<List<OrderGiftVo>> query(@RequestBody OrderGiftQueryForm orderGiftQueryForm) {
        if (orderGiftQueryForm.getUserId() == null) {
            orderGiftQueryForm.setUserId(SmartRequestUtil.getRequestUserId());
        }
        return orderGiftService.queryOrder(orderGiftQueryForm);
    }

}
