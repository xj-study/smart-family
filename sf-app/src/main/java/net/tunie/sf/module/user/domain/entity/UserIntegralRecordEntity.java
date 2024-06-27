package net.tunie.sf.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user_integral_record")
public class UserIntegralRecordEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private Long userId;

    private Integer integralChange;

    private Long refId;

    private Integer refType;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
