package net.tunie.sf.module.tag.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

@Data
@TableName("t_tag")
public class TagEntity extends BaseEntity {

    private String name;

    private Long userId;
}
