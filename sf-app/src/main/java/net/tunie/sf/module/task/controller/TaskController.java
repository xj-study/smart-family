package net.tunie.sf.module.task.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
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
    public ResponseDTO<Long> add(@RequestBody TaskAddForm taskAddForm) {
        Long requestUserId = SmartRequestUtil.getRequestUserId();
        taskAddForm.setUserId(requestUserId);
        return taskService.addTask(taskAddForm);
    }

    @GetMapping("/task/query")
    public ResponseDTO<List<TaskVo>> query(@RequestParam(required = false) String keyword) {
        return taskService.queryTask(SmartRequestUtil.getRequestUserId(), keyword);
    }

    @PostMapping("/task/update")
    public ResponseDTO<String> update(@RequestBody TaskUpdateForm taskUpdateForm) {
        taskUpdateForm.setUserId(SmartRequestUtil.getRequestUserId());
        return taskService.updateTask(taskUpdateForm);
    }

    @GetMapping("/task/delete/{id}")
    public ResponseDTO<String> updateDisableFlag(@PathVariable Long id) {
        return taskService.updateDisableFlag(id);
    }
}
