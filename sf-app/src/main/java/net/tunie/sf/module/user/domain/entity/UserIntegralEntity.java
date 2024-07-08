package net.tunie.sf.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BasicEntity;

import java.time.LocalDateTime;

@Data
@TableName("t_user_integral")
public class UserIntegralEntity extends BasicEntity {

    @TableId
    private Long userId;

    private Integer integral;

}
