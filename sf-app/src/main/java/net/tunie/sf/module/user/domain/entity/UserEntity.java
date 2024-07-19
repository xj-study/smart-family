package net.tunie.sf.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;
import net.tunie.sf.common.domain.BasicEntity;


@Data
@TableName("t_user")
public class UserEntity extends BaseEntity {

    private String name;

    private String nickname;

    private String mobile;

    private String password;

    private Long parentId;
}
