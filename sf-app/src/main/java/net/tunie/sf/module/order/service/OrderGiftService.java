package net.tunie.sf.module.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.constant.GiftConfigConst;
import net.tunie.sf.constant.OrderStatusConst;
import net.tunie.sf.constant.RecordTypeConst;
import net.tunie.sf.module.gift.domain.entity.GiftEntity;
import net.tunie.sf.module.gift.service.GiftService;
import net.tunie.sf.module.order.domain.dao.OrderGiftDao;
import net.tunie.sf.module.order.domain.entity.OrderGiftEntity;
import net.tunie.sf.module.order.domain.form.OrderGiftAddForm;
import net.tunie.sf.module.order.domain.form.OrderGiftQueryForm;
import net.tunie.sf.module.order.domain.vo.OrderGiftJsonVo;
import net.tunie.sf.module.order.domain.vo.OrderGiftVo;
import net.tunie.sf.module.user.domain.form.UserIntegralUpdateForm;
import net.tunie.sf.module.user.service.UserIntegralService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderGiftService {

    @Resource
    private OrderGiftDao orderGiftDao;

    @Resource
    private GiftService giftService;

    @Resource
    private UserIntegralService userIntegralService;

    public ResponseDTO<List<OrderGiftVo>> queryOrder(OrderGiftQueryForm orderGiftQueryForm) {

        List<OrderGiftEntity> orderGiftEntities = orderGiftDao.selectGiftList(orderGiftQueryForm);
        List<OrderGiftVo> orderGiftVos = SmartBeanUtil.copyList(orderGiftEntities, OrderGiftVo.class);

        return ResponseDTO.ok(orderGiftVos);
    }

    public ResponseDTO<String> updateOrderStatus(Long orderId, Integer status) {
        OrderGiftEntity orderGiftEntity = new OrderGiftEntity();
        orderGiftEntity.setOrderId(orderId);
        orderGiftEntity.setStatus(status);

        orderGiftDao.updateById(orderGiftEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<Long> createOrder(OrderGiftAddForm orderGiftAddForm) {

        if (orderGiftAddForm.getGiftId() == null) {
            return ResponseDTO.userErrorParams();
        }

        GiftEntity giftEntity = giftService.selectGiftById(orderGiftAddForm.getGiftId());
        if (giftEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST, "礼物数据不存在");
        }

        int price = (int) Math.round(giftEntity.getPrice() * GiftConfigConst.EXCHANGE_RATIO);
        int total = price * orderGiftAddForm.getNum();

        Integer userIntegral = userIntegralService.queryUserIntegral(orderGiftAddForm.getUserId());
        if (userIntegral < total) {
            return ResponseDTO.userErrorParams("用户积分不足");
        }

        OrderGiftEntity orderGiftEntity = SmartBeanUtil.copy(orderGiftAddForm, OrderGiftEntity.class);
        orderGiftEntity.setPrice(price);
        orderGiftEntity.setTotalPrice(total);
        orderGiftEntity.setName(giftEntity.getName());
        orderGiftEntity.setContent(giftEntity.getContent());
        orderGiftEntity.setStatus(OrderStatusConst.UNSHIPPED);

        orderGiftDao.insert(orderGiftEntity);

        //
        UserIntegralUpdateForm userIntegralUpdateForm = new UserIntegralUpdateForm();
        userIntegralUpdateForm.setUserId(orderGiftEntity.getUserId());
        userIntegralUpdateForm.setRefId(orderGiftEntity.getOrderId());
        userIntegralUpdateForm.setRefType(RecordTypeConst.ORDER_GIFT);
        userIntegralUpdateForm.setIntegralChange(-total);

        ObjectMapper objectMapper = new ObjectMapper();
        OrderGiftJsonVo orderGiftJsonVo = SmartBeanUtil.copy(orderGiftEntity, OrderGiftJsonVo.class);
        try {
            String content = objectMapper.writeValueAsString(orderGiftJsonVo);
            userIntegralUpdateForm.setContent(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        userIntegralService.update(userIntegralUpdateForm);


        return ResponseDTO.ok(orderGiftEntity.getOrderId());
    }
}
