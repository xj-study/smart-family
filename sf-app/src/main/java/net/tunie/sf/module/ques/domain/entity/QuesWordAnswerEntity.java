package net.tunie.sf.module.ques.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.tunie.sf.common.domain.BaseEntity;

@Data
@TableName("t_ques_word_answer")
public class QuesWordAnswerEntity extends BaseEntity {
    private Long quesWordId;
    private Long userId;
    private String userAnswer;
    private String options;
    private Boolean wrongFlag;
}
