package net.tunie.sf.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;
import net.tunie.sf.common.domain.BasicEntity;

import java.time.LocalDateTime;

@Data
@TableName("t_user_integral_record")
public class UserIntegralRecordEntity extends BaseEntity {

    private Long userId;

    private Integer integralChange;

    private Long refId;
}
