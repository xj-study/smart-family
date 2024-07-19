package net.tunie.sf.module.game.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("t_game")
@Data
public class GameEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String name;

    private String content;

    private Boolean disableFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
