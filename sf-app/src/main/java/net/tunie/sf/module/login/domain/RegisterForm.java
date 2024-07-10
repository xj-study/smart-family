package net.tunie.sf.module.login.domain;

import lombok.Data;

@Data
public class RegisterForm {

    private String name;

    private String password;

    private String confirmPassword;
}
