package net.tunie.sf.module.tag.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

@Data
@TableName("t_tag_ref")
public class TagRefEntity extends BaseEntity {
    private Integer refType;

    private Long refId;

    private Long tagId;
}
