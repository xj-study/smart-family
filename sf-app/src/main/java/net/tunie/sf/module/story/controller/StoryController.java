package net.tunie.sf.module.story.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.module.story.constant.StoryStatusConst;
import net.tunie.sf.module.story.domain.form.StoryAddForm;
import net.tunie.sf.module.story.domain.form.StoryQueryForm;
import net.tunie.sf.module.story.domain.form.StoryUpdateForm;
import net.tunie.sf.module.story.domain.vo.StoryVo;
import net.tunie.sf.module.story.service.StoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryController {

    @Resource
    private StoryService storyService;

    @PostMapping("/story/add")
    public ResponseDTO<Long> add(@RequestBody StoryAddForm storyAddForm) {
        storyAddForm.setUserId(SmartRequestUtil.getRequestUserId());
        return storyService.addStory(storyAddForm);
    }

    @PostMapping("/story/update")
    public ResponseDTO<Integer> update(@RequestBody StoryUpdateForm storyUpdateForm) {
        return storyService.updateStory(storyUpdateForm);
    }

    @PostMapping("/story/{storyId}/publish")
    public ResponseDTO<Integer> update(@PathVariable Long storyId) {
        StoryUpdateForm storyUpdateForm = new StoryUpdateForm(storyId);
        storyUpdateForm.setStatus(StoryStatusConst.PUBLISHED);

        return storyService.updateStory(storyUpdateForm);
    }

    @PostMapping("/story/{storyId}/offshelf")
    public ResponseDTO<Integer> updateOffShelf(@PathVariable Long storyId) {
        StoryUpdateForm storyUpdateForm = new StoryUpdateForm(storyId);
        storyUpdateForm.setStatus(StoryStatusConst.OFF_SHELF);

        return storyService.updateStory(storyUpdateForm);
    }

    @PostMapping("/story/query")
    public ResponseDTO<List<StoryVo>> query(@RequestBody StoryQueryForm storyQueryForm) {
        storyQueryForm.setUserId(SmartRequestUtil.getRequestUserId());
        return storyService.queryStoryList(storyQueryForm);
    }

    @GetMapping("/story/{storyId}/query")
    public ResponseDTO<StoryVo> query(@PathVariable Long storyId) {
        return storyService.queryStory(storyId);
    }

}
