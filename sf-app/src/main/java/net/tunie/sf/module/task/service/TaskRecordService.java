package net.tunie.sf.module.task.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.common.utils.SmartUserUtil;
import net.tunie.sf.constant.TaskStatusConst;
import net.tunie.sf.constant.RecordTypeConst;
import net.tunie.sf.constant.TaskTypeConst;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.task.domain.dao.TaskDao;
import net.tunie.sf.module.task.domain.dao.TaskIntegralDao;
import net.tunie.sf.module.task.domain.dao.TaskRecordDao;
import net.tunie.sf.module.task.domain.entity.TaskEntity;
import net.tunie.sf.module.task.domain.entity.TaskIntegralEntity;
import net.tunie.sf.module.task.domain.entity.TaskRecordEntity;
import net.tunie.sf.module.task.domain.form.TaskRecordCompleteForm;
import net.tunie.sf.module.task.domain.form.TaskRecordQueryForm;
import net.tunie.sf.module.task.domain.vo.TaskJsonVo;
import net.tunie.sf.module.task.domain.vo.TaskRecordVo;
import net.tunie.sf.module.user.domain.form.UserIntegralUpdateForm;
import net.tunie.sf.module.user.service.UserIntegralService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskRecordService {
    @Resource
    private TaskRecordDao taskRecordDao;

    @Resource
    private TaskDao taskDao;

    @Resource
    private TaskIntegralDao taskIntegralDao;

    @Resource
    private UserIntegralService userIntegralService;

    public ResponseDTO<List<TaskRecordVo>> queryDailyTaskRecord(RequestUser requestUser, LocalDate localDate, TaskRecordQueryForm taskRecordQueryForm) {
        Long taskUserId = requestUser.getUserId();
        if (SmartUserUtil.getUserChildFlag(requestUser.getType())) {
            taskUserId = requestUser.getParentId();
        }

        Long recordUserId = requestUser.getUserId();
        if (taskRecordQueryForm.getId() != null) {
            recordUserId = taskRecordQueryForm.getId();
        }

        List<TaskRecordVo> taskRecordVos = taskRecordDao.queryDailyTaskRecord(taskUserId, recordUserId, localDate, taskRecordQueryForm.getStatus());
        return ResponseDTO.ok(taskRecordVos);
    }

    // 检查一下任务更新的状态
    private Integer getTaskUpdateStatus(TaskRecordEntity taskRecordEntity, TaskEntity taskEntity, Integer status) {
        if (taskEntity.getTaskType() == TaskTypeConst.COMMON) {
            if (status == TaskStatusConst.COMPLETE && taskRecordEntity.getStatus() == TaskStatusConst.INIT && taskEntity.getVerifyFlag()) {
                return TaskStatusConst.WAIT_VERIFY;
            }
        }
        return status;
    }



    public ResponseDTO<Integer> updateTaskStatus(TaskRecordEntity taskRecordEntity, Integer status) {
        if (taskRecordEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        TaskEntity taskEntity = taskDao.selectById(taskRecordEntity.getTaskId());
        status = getTaskUpdateStatus(taskRecordEntity, taskEntity, status);

        taskRecordEntity.setStatus(status);
        taskRecordDao.updateById(taskRecordEntity);

        // 更新积分
        if (TaskStatusConst.COMPLETE == status) {
            TaskIntegralEntity taskIntegralEntity = taskIntegralDao.selectById(taskRecordEntity.getTaskId());

            UserIntegralUpdateForm userIntegralUpdateForm = new UserIntegralUpdateForm();
            userIntegralUpdateForm.setIntegralChange(taskIntegralEntity.getIntegral());
            userIntegralUpdateForm.setUserId(taskRecordEntity.getUserId());
            userIntegralUpdateForm.setRefType(RecordTypeConst.TASK);
            userIntegralUpdateForm.setRefId(taskRecordEntity.getId());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            TaskJsonVo taskJsonVo = SmartBeanUtil.copy(taskEntity, TaskJsonVo.class);
            taskJsonVo.setTaskDate(taskRecordEntity.getTaskDate());
            taskJsonVo.setId(taskRecordEntity.getId());
            try {
                String content = objectMapper.writeValueAsString(taskJsonVo);
                userIntegralUpdateForm.setContent(content);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            userIntegralService.update(userIntegralUpdateForm);
        }

        return ResponseDTO.ok(status);
    }

    public ResponseDTO<Integer> updateTaskStatus(Long id, Integer status) {
        TaskRecordEntity taskRecordEntity = taskRecordDao.selectById(id);
        return this.updateTaskStatus(taskRecordEntity, status);
    }

    public ResponseDTO<Integer> updateTaskStatus(TaskRecordCompleteForm taskRecordCompleteForm) {
        if (taskRecordCompleteForm.getTaskDate() == null) {
            taskRecordCompleteForm.setTaskDate(LocalDate.now());
        }
        TaskRecordEntity queryTaskRecordEntity = new TaskRecordEntity();
        queryTaskRecordEntity.setTaskId(taskRecordCompleteForm.getTaskId());
        queryTaskRecordEntity.setUserId(taskRecordCompleteForm.getUserId());
        queryTaskRecordEntity.setTaskDate(taskRecordCompleteForm.getTaskDate());
        QueryWrapper<TaskRecordEntity> query = Wrappers.query(queryTaskRecordEntity);
        TaskRecordEntity selectOne = taskRecordDao.selectOne(query);
        if (selectOne != null) {
            return this.updateTaskStatus(selectOne, taskRecordCompleteForm.getStatus());
        } else {

            TaskRecordEntity taskRecordEntity = SmartBeanUtil.copy(taskRecordCompleteForm, TaskRecordEntity.class);
            taskRecordEntity.setStatus(TaskStatusConst.INIT);
            taskRecordDao.insert(taskRecordEntity);
            return this.updateTaskStatus(taskRecordEntity, taskRecordCompleteForm.getStatus());
        }

    }

    public ResponseDTO<String> verify(RequestUser user, Long id) {
        // 没权限
        if (SmartUserUtil.getUserChildFlag(user.getType())) {
            return ResponseDTO.error(UserErrorCode.NO_PERMISSION);
        }

        // 数据不存在
        TaskRecordEntity taskRecordEntity = taskRecordDao.selectById(id);
        if (taskRecordEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        // 不满足审核状态
        if (taskRecordEntity.getStatus() != TaskStatusConst.WAIT_VERIFY) {
            return ResponseDTO.userErrorParams("任务状态不满足审核条件");
        }

        // 审核通过更新
        taskRecordEntity.setStatus(TaskStatusConst.COMPLETE);
        taskRecordDao.updateById(taskRecordEntity);

        return ResponseDTO.ok();
    }
}
