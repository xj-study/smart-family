package net.tunie.sf.common.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BasicEntity {

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

}
