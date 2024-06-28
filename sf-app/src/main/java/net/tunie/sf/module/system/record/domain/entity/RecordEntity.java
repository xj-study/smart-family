package net.tunie.sf.module.system.record.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_record")
public class RecordEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long refId;

    private Integer refType;

    private Long userId;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
