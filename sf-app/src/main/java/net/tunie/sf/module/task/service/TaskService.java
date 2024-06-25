package net.tunie.sf.module.task.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.task.domain.dao.TaskDao;
import net.tunie.sf.module.task.domain.entity.TaskEntity;
import net.tunie.sf.module.task.domain.form.TaskAddForm;
import net.tunie.sf.module.task.domain.form.TaskUpdateForm;
import net.tunie.sf.module.task.domain.vo.TaskVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Resource
    private TaskDao taskDao;

    public ResponseDTO<String> addTask(TaskAddForm taskAddForm) {
        TaskEntity taskEntity = SmartBeanUtil.copy(taskAddForm, TaskEntity.class);
        taskDao.insert(taskEntity);

        return ResponseDTO.ok();
    }

    public ResponseDTO<List<TaskVo>> queryTask(Long requestUserId) {
        QueryWrapper<TaskEntity> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id", requestUserId);
        queryWrapper.eq("disable_flag", 0);
        List<TaskEntity> taskUserEntities = taskDao.selectList(queryWrapper);
        List<TaskVo> taskUserVos = SmartBeanUtil.copyList(taskUserEntities, TaskVo.class);
        return ResponseDTO.ok(taskUserVos);
    }

    public ResponseDTO<String> updateTask(TaskUpdateForm taskUpdateForm) {
        TaskEntity taskEntity = taskDao.selectById(taskUpdateForm.getTaskId());
        if (taskEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        if (!taskEntity.getUserId().equals(taskUpdateForm.getUserId())) {
            return ResponseDTO.error(UserErrorCode.NO_PERMISSION, "您没有权限修改");
        }

        TaskEntity updateTaskEntity = SmartBeanUtil.copy(taskUpdateForm, TaskEntity.class);
        taskDao.updateById(updateTaskEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> updateDisableFlag(Long id) {
        TaskEntity taskEntity = taskDao.selectById(id);
        taskDao.updateDisableFlag(id, !taskEntity.getDisableFlag());
        return ResponseDTO.ok();
    }
}
