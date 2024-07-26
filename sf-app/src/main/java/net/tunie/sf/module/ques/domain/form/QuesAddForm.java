package net.tunie.sf.module.ques.domain.form;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public class QuesAddForm extends QuesQueryForm {

    private JSONObject rules;
}
