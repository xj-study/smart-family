package net.tunie.sf.module.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;
import net.tunie.sf.common.domain.BasicEntity;

import java.time.LocalDateTime;

@Data
@TableName("t_order_gift")
public class OrderGiftEntity extends BaseEntity {

    private Long userId;

    private Long giftId;

    private Integer status;

    private Integer num;

    private String name;

    private String content;

    private Integer price;

    private Integer totalPrice;

}

