package net.tunie.sf.module.gift.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_gift")
public class GiftEntity {

    @TableId(type = IdType.AUTO)
    private Long giftId;

    private Long userId;

    private String title;

    private String content;

    private Double price;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
