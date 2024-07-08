package net.tunie.sf.module.story.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.module.story.domain.form.*;
import net.tunie.sf.module.story.domain.vo.StoryLevelVo;
import net.tunie.sf.module.story.domain.vo.StoryVo;
import net.tunie.sf.module.story.service.StoryLevelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryLevelController {

    @Resource
    private StoryLevelService storyLevelService;

    @PostMapping("/story/level/add")
    public ResponseDTO<Long> add(@RequestBody StoryLevelAddForm storyLevelAddForm) {
        return storyLevelService.addStoryLevel(storyLevelAddForm);
    }

    @PostMapping("/story/level/update")
    public ResponseDTO<String> update(@RequestBody StoryLevelUpdateForm storyLevelUpdateForm) {
        return storyLevelService.updateStoryLevel(storyLevelUpdateForm);
    }

    @GetMapping("/story/{storyId}/level/query")
    public ResponseDTO<List<StoryLevelVo>> query(@PathVariable Long storyId, @RequestParam Boolean disableFlag) {
        return storyLevelService.queryStoryLevelList(storyId, disableFlag);
    }

    @GetMapping("/story/level/{storyLevelId}/query")
    public ResponseDTO<StoryLevelVo> query(@PathVariable Long storyLevelId) {

        return storyLevelService.queryStoryLevel(storyLevelId);
    }

    @GetMapping("/story/level/{storyLevelId}/update/disable")
    public ResponseDTO<String> updateDisableFlag(@PathVariable Long storyLevelId) {
        return storyLevelService.updateStoryLevelDisable(storyLevelId);
    }
}
