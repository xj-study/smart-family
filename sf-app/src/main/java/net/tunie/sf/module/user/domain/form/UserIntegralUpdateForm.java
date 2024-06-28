package net.tunie.sf.module.user.domain.form;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserIntegralUpdateForm {

    private Long userId;

    private Integer integralChange;

    private Long refId;

    private Integer refType;

    private String content;


}
