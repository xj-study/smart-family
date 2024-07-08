package net.tunie.sf.module.gift.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

import java.time.LocalDateTime;

@Data
@TableName("t_gift")
public class GiftEntity extends BaseEntity {

    private Long userId;

    private String name;

    private String content;

    private Double price;

}
