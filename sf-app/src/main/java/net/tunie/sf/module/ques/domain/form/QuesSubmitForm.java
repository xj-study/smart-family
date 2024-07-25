package net.tunie.sf.module.ques.domain.form;

import lombok.Data;

import java.util.List;

@Data
public class QuesSubmitForm {

    private List<QuesAnswerForm> answers;
}
