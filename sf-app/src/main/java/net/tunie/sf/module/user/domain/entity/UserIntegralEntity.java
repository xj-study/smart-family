package net.tunie.sf.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user_integral")
public class UserIntegralEntity {

    @TableId
    private Long userId;

    private Integer integral;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
