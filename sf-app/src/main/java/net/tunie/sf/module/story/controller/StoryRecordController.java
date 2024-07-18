package net.tunie.sf.module.story.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.story.constant.StoryRecordStatusConst;
import net.tunie.sf.module.story.domain.form.*;
import net.tunie.sf.module.story.domain.vo.StoryRecordVo;
import net.tunie.sf.module.story.service.StoryRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryRecordController {

    @Resource
    private StoryRecordService storyRecordService;

    @PostMapping("/story/record/query")
    public ResponseDTO<List<StoryRecordVo>> query(@RequestBody StoryRecordQueryForm storyRecordQueryForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        return storyRecordService.queryStoryRecordList(requestUser, storyRecordQueryForm);
    }

    @GetMapping("/story/record/query/{storyId}")
    public ResponseDTO<StoryRecordVo> queryStory(@PathVariable Long storyId) {
        return storyRecordService.queryStoryRecord(SmartRequestUtil.getRequestUserId(), storyId);
    }

    @PostMapping("/story/record/active/{storyId}")
    public ResponseDTO<Integer> active(@PathVariable long storyId) {
        StoryRecordUpdateStatusForm storyRecordUpdateStatusForm = new StoryRecordUpdateStatusForm(storyId);
        storyRecordUpdateStatusForm.setStatus(StoryRecordStatusConst.ACTIVE);
        storyRecordUpdateStatusForm.setUserId(SmartRequestUtil.getRequestUserId());
        return storyRecordService.updateStoryRecordStatus(storyRecordUpdateStatusForm);
    }

    @PostMapping("/story/record/level/pass")
    public ResponseDTO<Long> pass(@RequestBody StoryRecordUpdateLevelForm storyRecordUpdateLevelForm) {
        storyRecordUpdateLevelForm.setUserId(SmartRequestUtil.getRequestUserId());
        return storyRecordService.updateStoryRecordLevel(storyRecordUpdateLevelForm);
    }


}
