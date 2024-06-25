package net.tunie.sf.module.login.domain;

import lombok.Data;

@Data
public class RequestUser {

    private Long userId;

    private Long parentId;

    private String userName;

    private Integer type;
}
