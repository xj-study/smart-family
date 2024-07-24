package net.tunie.sf.module.ques.utils;

import jakarta.annotation.Resource;
import net.tunie.sf.common.service.RulesService;
import net.tunie.sf.module.ques.constant.QuesTypeConst;
import net.tunie.sf.module.story.service.StoryLevelService;
import net.tunie.sf.module.task.service.TaskService;
import org.springframework.stereotype.Component;

@Component
public class QuesRulesFactory {

    @Resource
    private TaskService taskService;

    @Resource
    private StoryLevelService storyLevelService;

    public RulesService getService(Integer refType) {
        return switch (refType) {
            case QuesTypeConst.TASK -> taskService;
            case QuesTypeConst.STORY_LEVEL -> storyLevelService;
            default -> null;
        };
    }
}
