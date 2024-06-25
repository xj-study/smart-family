package net.tunie.sf.module.login.domain;

import lombok.Data;

@Data
public class LoginResultVo {
    private Long userId;

    private String userName;

    private Integer type;

    private String token;
}
