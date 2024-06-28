package net.tunie.sf.module.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_order_gift")
public class OrderGiftEntity {

    @TableId(type= IdType.AUTO)
    private Long orderId;

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

