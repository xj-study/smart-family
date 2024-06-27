package net.tunie.sf.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user")
public class UserEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String nickname;

    private String mobile;

    private String password;

    private Long parentId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
