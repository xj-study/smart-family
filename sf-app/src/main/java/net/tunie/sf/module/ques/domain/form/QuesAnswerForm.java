package net.tunie.sf.module.ques.domain.form;

import lombok.Data;

@Data
public class QuesAnswerForm {
   private Long quesWordId;
   private Long userId;
   private String userAnswer;
   private String options;
   private Boolean wrongFlag;
}
