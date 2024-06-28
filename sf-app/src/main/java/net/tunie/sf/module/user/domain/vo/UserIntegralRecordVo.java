package net.tunie.sf.module.user.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserIntegralRecordVo {

    private Long id;

    private Integer integralChange;

    private Long refId;

    private Integer refType;

    private String content;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
