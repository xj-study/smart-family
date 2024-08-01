package net.tunie.sf.module.tag.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.module.tag.domain.form.TagAddForm;
import net.tunie.sf.module.tag.domain.form.TagUpdateForm;
import net.tunie.sf.module.tag.domain.vo.TagVo;
import net.tunie.sf.module.tag.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    @Resource
    private TagService tagService;
    @PostMapping("/tag/add")
    public ResponseDTO<Long> add(@RequestBody TagAddForm tagAddForm) {
        Long requestUserId = SmartRequestUtil.getRequestUserId();
        tagAddForm.setUserId(requestUserId);
        return tagService.addTag(tagAddForm);
    }

    @GetMapping("/tag/query")
    public ResponseDTO<List<TagVo>> query(@RequestParam(required = false) String keyword) {
        return tagService.queryTag(SmartRequestUtil.getRequestUserId(), keyword);
    }

    @PostMapping("/tag/update")
    public ResponseDTO<String> update(@RequestBody TagUpdateForm tagUpdateForm) {
        tagUpdateForm.setUserId(SmartRequestUtil.getRequestUserId());
        return tagService.updateTag(tagUpdateForm);
    }

    @PostMapping("/tag/delete/{id}")
    public ResponseDTO<String> delete(@PathVariable Long id) {
        return tagService.deleteTag(SmartRequestUtil.getRequestUserId(), id);
    }
}
