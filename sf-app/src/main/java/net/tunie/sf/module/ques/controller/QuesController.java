package net.tunie.sf.module.ques.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.module.ques.domain.form.QuesQueryForm;
import net.tunie.sf.module.ques.domain.form.QuesSubmitForm;
import net.tunie.sf.module.ques.domain.vo.QuesVo;
import net.tunie.sf.module.ques.service.QuesService;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuesController {

    @Resource
    private QuesService quesService;

    @PostMapping("/ques/query")
    public ResponseDTO<QuesVo> query(@RequestBody QuesQueryForm quesQueryForm) {
        return quesService.queryQues(quesQueryForm);
    }

    @PostMapping("/ques/submit")
    public ResponseDTO<Boolean> query(@RequestBody QuesSubmitForm quesSubmitForm) {
        Long userId = SmartRequestUtil.getRequestUserId();
        quesSubmitForm.getAnswers().forEach(item -> item.setUserId(userId));
        return quesService.submitQues(quesSubmitForm);
    }

    @GetMapping("/word/remove/{id}")
    public ResponseDTO<String> remove(@PathVariable Long id) {
        return quesService.removeWord(id);
    }

}
