package net.tunie.sf.module.ques.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.module.ques.domain.form.QuesQueryForm;
import net.tunie.sf.module.ques.domain.vo.QuesVo;
import net.tunie.sf.module.ques.service.QuesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuesController {

    @Resource
    private QuesService quesService;

    @PostMapping("/ques/query")
    public ResponseDTO<QuesVo> query(@RequestBody QuesQueryForm quesQueryForm) {
        return quesService.queryQues(quesQueryForm);
    }
}
