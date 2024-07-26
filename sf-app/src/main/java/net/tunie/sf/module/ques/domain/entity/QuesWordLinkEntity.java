package net.tunie.sf.module.ques.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BasicEntity;

@Data
@TableName("t_ques_word_link")
public class QuesWordLinkEntity extends BasicEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer sort;

    private Long quesId;

    private Long quesWordId;
}
