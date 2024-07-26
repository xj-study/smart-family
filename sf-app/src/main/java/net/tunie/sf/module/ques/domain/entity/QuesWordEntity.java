package net.tunie.sf.module.ques.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;
import net.tunie.sf.module.word.domain.vo.WordVo;

import java.util.List;

@Data
@TableName("t_ques_word")
public class QuesWordEntity extends BaseEntity {
    private Long wordId;
    private Integer type;
    private Integer fillLevel;
    private Integer selectNum;
    private String options;

    @TableField(exist = false)
    private List<WordVo> optionList;
}
