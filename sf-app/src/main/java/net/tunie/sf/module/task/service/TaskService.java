package net.tunie.sf.module.task.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.service.RulesService;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.task.domain.dao.TaskDao;
import net.tunie.sf.module.task.domain.entity.TaskEntity;
import net.tunie.sf.module.task.domain.entity.TaskIntegralEntity;
import net.tunie.sf.module.task.domain.form.TaskAddForm;
import net.tunie.sf.module.task.domain.form.TaskUpdateForm;
import net.tunie.sf.module.task.domain.vo.TaskVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService extends ServiceImpl<TaskDao, TaskEntity> implements RulesService {

    @Resource
    private TaskIntegralService taskIntegralService;

    public ResponseDTO<Long> addTask(TaskAddForm taskAddForm) {
        TaskEntity taskEntity = SmartBeanUtil.copy(taskAddForm, TaskEntity.class);
        this.save(taskEntity);

        TaskIntegralEntity taskIntegralEntity = new TaskIntegralEntity();
        taskIntegralEntity.setTaskId(taskEntity.getId());
        taskIntegralEntity.setIntegral(taskAddForm.getIntegral());
        taskIntegralService.save(taskIntegralEntity);

        return ResponseDTO.ok(taskEntity.getId());
    }

    public ResponseDTO<List<TaskVo>> queryTask(Long requestUserId) {
        List<TaskVo> taskVos = this.baseMapper.getTaskList(requestUserId);
        return ResponseDTO.ok(taskVos);
    }

    public ResponseDTO<String> updateTask(TaskUpdateForm taskUpdateForm) {
        TaskEntity taskEntity = this.getById(taskUpdateForm.getId());
        if (taskEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        if (!taskEntity.getUserId().equals(taskUpdateForm.getUserId())) {
            return ResponseDTO.error(UserErrorCode.NO_PERMISSION, "您没有权限修改");
        }

        TaskEntity updateTaskEntity = SmartBeanUtil.copy(taskUpdateForm, TaskEntity.class);
        this.updateById(updateTaskEntity);

        TaskIntegralEntity taskIntegralEntity = this.taskIntegralService.getById(taskUpdateForm.getId());
        if (taskIntegralEntity == null) {
            taskIntegralEntity = SmartBeanUtil.copy(taskUpdateForm, TaskIntegralEntity.class);
            this.taskIntegralService.save(taskIntegralEntity);
        } else {
            taskIntegralEntity.setIntegral(taskUpdateForm.getIntegral());
            this.taskIntegralService.updateById(taskIntegralEntity);
        }

        return ResponseDTO.ok();
    }

    public ResponseDTO<String> updateDisableFlag(Long id) {
        TaskEntity taskEntity = this.getById(id);
        if (taskEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        this.removeById(id);

        return ResponseDTO.ok();
    }

    @Override
    public JSONObject getRules(Long id) {
        TaskEntity entity = this.getById(id);
        if (entity.getRules() != null) {
            return JSON.parseObject(entity.getRules());
        }
        return null;
    }
}
