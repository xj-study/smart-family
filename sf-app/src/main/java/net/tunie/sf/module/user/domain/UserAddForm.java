package net.tunie.sf.module.user.domain;

import lombok.Data;

@Data
public class UserAddForm {

    private String name;

    private String nickname;

    private String mobile;

    private String password;
}
