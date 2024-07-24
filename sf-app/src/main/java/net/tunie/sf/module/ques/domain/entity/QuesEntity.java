package net.tunie.sf.module.ques.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

@Data
@TableName("t_ques")
public class QuesEntity extends BaseEntity {

    private Integer refType;
    private Long refId;

    public QuesEntity() {
    }

    public QuesEntity(Integer refType, Long refId) {
        this.refType = refType;
        this.refId = refId;
    }
}
