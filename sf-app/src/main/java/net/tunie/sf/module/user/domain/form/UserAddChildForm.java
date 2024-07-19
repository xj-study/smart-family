package net.tunie.sf.module.user.domain.form;

import lombok.Data;

@Data
public class UserAddChildForm {

    private String name;

    private String nickname;

    private String password;

    private String confirmPassword;

    private Long parentId;
}
