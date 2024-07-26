package net.tunie.sf.module.word.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.PageDTO;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.module.word.domain.form.WordAddForm;
import net.tunie.sf.module.word.domain.form.WordQueryForm;
import net.tunie.sf.module.word.domain.form.WordUpdateForm;
import net.tunie.sf.module.word.domain.vo.WordVo;
import net.tunie.sf.module.word.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WordController {
    @Resource
    private WordService wordService;

    @PostMapping("/word/query")
    public ResponseDTO<PageDTO<WordVo>> query(@RequestBody WordQueryForm wordQueryForm){
        return wordService.queryWordPage(wordQueryForm);
    }

    @GetMapping("/word/stat/query")
    public ResponseDTO<List<Integer>> query(){
        return wordService.queryStat();
    }

    @PostMapping("/word/add")
    public ResponseDTO<Long> query(@RequestBody WordAddForm wordAddForm) {
        return wordService.addWord(wordAddForm);
    }


    @PostMapping("/word/update")
    public ResponseDTO<String> update(@RequestBody WordUpdateForm wordUpdateForm) {
        return wordService.updateWord(wordUpdateForm);
    }

    @GetMapping("/word/remove/{id}")
    public ResponseDTO<String> remove(@PathVariable Long id) {
        return wordService.removeWord(id);
    }

}
