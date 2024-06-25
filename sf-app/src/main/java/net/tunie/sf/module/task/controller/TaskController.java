package net.tunie.sf.module.task.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.common.utils.SmartUserUtil;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.task.domain.form.TaskAddForm;
import net.tunie.sf.module.task.domain.form.TaskUpdateForm;
import net.tunie.sf.module.task.domain.vo.TaskVo;
import net.tunie.sf.module.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/task/add")
    public ResponseDTO<String> add(@RequestBody TaskAddForm taskAddForm) {
        Long requestUserId = SmartRequestUtil.getRequestUserId();
        taskAddForm.setUserId(requestUserId);
        return taskService.addTask(taskAddForm);
    }

    @GetMapping("/task/query")
    public ResponseDTO<List<TaskVo>> query() {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        boolean userChildFlag = SmartUserUtil.getUserChildFlag(requestUser.getType());
        long requestUserId = userChildFlag ? requestUser.getParentId() : requestUser.getUserId();
        return taskService.queryTask(requestUserId);
    }

    @PostMapping("/task/update")
    public ResponseDTO<String> update(@RequestBody TaskUpdateForm taskUpdateForm) {
        taskUpdateForm.setUserId(SmartRequestUtil.getRequestUserId());
        return taskService.updateTask(taskUpdateForm);
    }

    @GetMapping("/task/disable/{id}")
    public ResponseDTO<String> updateDisableFlag(@PathVariable Long id) {
        return taskService.updateDisableFlag(id);
    }
}
