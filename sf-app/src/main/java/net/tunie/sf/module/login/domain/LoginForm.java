package net.tunie.sf.module.login.domain;

import lombok.Data;

@Data
public class LoginForm {
    private String userName;
    private String mobile;
    private String password;
}
