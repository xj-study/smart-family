package net.tunie.sf.module.user.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVo {

    private Long id;

    private String name;

    private String nickname;

    private String mobile;

    private Long parentId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
