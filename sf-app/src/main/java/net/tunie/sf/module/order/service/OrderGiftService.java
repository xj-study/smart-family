package net.tunie.sf.module.order.service;

import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.constant.GiftConfigConst;
import net.tunie.sf.constant.OrderStatusConst;
import net.tunie.sf.module.gift.domain.entity.GiftEntity;
import net.tunie.sf.module.gift.service.GiftService;
import net.tunie.sf.module.order.domain.dao.OrderGiftDao;
import net.tunie.sf.module.order.domain.entity.OrderGiftEntity;
import net.tunie.sf.module.order.domain.form.OrderGiftAddForm;
import org.springframework.stereotype.Service;

import java.io.Console;

@Service
public class OrderGiftService {

    @Resource
    private OrderGiftDao orderGiftDao;

    @Resource
    private GiftService giftService;

    public ResponseDTO<Long> createOrder(OrderGiftAddForm orderGiftAddForm) {

        if (orderGiftAddForm.getGiftId() == null) {
            return ResponseDTO.userErrorParams();
        }

        GiftEntity giftEntity = giftService.selectGiftById(orderGiftAddForm.getGiftId());
        if (giftEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST, "礼物数据不存在");
        }

        OrderGiftEntity orderGiftEntity = SmartBeanUtil.copy(orderGiftAddForm, OrderGiftEntity.class);

        int price = (int) Math.round(giftEntity.getPrice() * GiftConfigConst.EXCHANGE_RATIO);
        int total = price * orderGiftEntity.getNum();
        orderGiftEntity.setPrice(price);
        orderGiftEntity.setTotalPrice(total);
        orderGiftEntity.setName(giftEntity.getName());
        orderGiftEntity.setContent(giftEntity.getContent());
        orderGiftEntity.setStatus(OrderStatusConst.UNSHIPPED);

        orderGiftDao.insert(orderGiftEntity);

        return ResponseDTO.ok(orderGiftEntity.getOrderId());
    }
}
