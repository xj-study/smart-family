package net.tunie.sf.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class BaseEntity extends BasicEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    @TableLogic
    private Integer disableFlag;
}
