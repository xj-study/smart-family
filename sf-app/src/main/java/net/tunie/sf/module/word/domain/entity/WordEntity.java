package net.tunie.sf.module.word.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

@Data
@TableName("t_word")
public class WordEntity extends BaseEntity {

    private Integer level;

    private String enValue;

    private String zhValue;

}
