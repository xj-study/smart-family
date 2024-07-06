package net.tunie.sf.module.task.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.common.utils.SmartUserUtil;
import net.tunie.sf.constant.TaskStatusConst;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.task.domain.form.TaskRecordCompleteForm;
import net.tunie.sf.module.task.domain.form.TaskRecordQueryForm;
import net.tunie.sf.module.task.domain.vo.TaskRecordVo;
import net.tunie.sf.module.task.service.TaskRecordService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TaskRecordController {

    @Resource
    private TaskRecordService taskRecordService;

    @PostMapping("/task/record/query")
    public ResponseDTO<List<TaskRecordVo>> query(@RequestBody TaskRecordQueryForm taskRecordQueryForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        return taskRecordService.queryDailyTaskRecord(requestUser, taskRecordQueryForm);
    }

    @GetMapping("/task/record/complete/{id}")
    public ResponseDTO<Integer> complete(@PathVariable Long id) {
        return taskRecordService.updateTaskStatus(id, TaskStatusConst.COMPLETE);
    }

    @PostMapping("/task/record/complete")
    public ResponseDTO<Integer> complete(@RequestBody TaskRecordCompleteForm taskRecordCompleteForm) {
        taskRecordCompleteForm.setUserId(SmartRequestUtil.getRequestUserId());
        taskRecordCompleteForm.setStatus(TaskStatusConst.COMPLETE);
        return taskRecordService.updateTaskStatus(taskRecordCompleteForm);
    }

    @PostMapping("/task/record/verify/{id}")
    public ResponseDTO<String> verify(@PathVariable Long id) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        return taskRecordService.verify(requestUser, id);
    }
}
