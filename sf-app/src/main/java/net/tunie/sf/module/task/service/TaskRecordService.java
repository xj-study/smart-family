package net.tunie.sf.module.task.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.common.utils.SmartUserUtil;
import net.tunie.sf.constant.TaskStatusConst;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.task.domain.dao.TaskDao;
import net.tunie.sf.module.task.domain.dao.TaskRecordDao;
import net.tunie.sf.module.task.domain.entity.TaskEntity;
import net.tunie.sf.module.task.domain.entity.TaskRecordEntity;
import net.tunie.sf.module.task.domain.form.TaskRecordCompleteForm;
import net.tunie.sf.module.task.domain.form.TaskRecordQueryForm;
import net.tunie.sf.module.task.domain.vo.TaskRecordVo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskRecordService {
    @Resource
    private TaskRecordDao taskRecordDao;

    @Resource
    private TaskDao taskDao;

    public ResponseDTO<List<TaskRecordVo>> queryDailyTaskRecord(RequestUser requestUser, LocalDate localDate, TaskRecordQueryForm taskRecordQueryForm) {
        Long taskUserId = requestUser.getUserId();
        if (SmartUserUtil.getUserChildFlag(requestUser.getType())) {
            taskUserId = requestUser.getParentId();
        }

        Long recordUserId = requestUser.getUserId();
        if (taskRecordQueryForm.getId() != null) {
            recordUserId = taskRecordQueryForm.getId();
        }

        List<TaskRecordVo> taskRecordVos = taskRecordDao.queryDailyTaskRecord(taskUserId, recordUserId, localDate);
        return ResponseDTO.ok(taskRecordVos);
    }

    public ResponseDTO<Integer> updateTaskStatus(Long id, Integer status) {
        TaskRecordEntity taskRecordEntity = taskRecordDao.selectById(id);
        if (taskRecordEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        if (TaskStatusConst.COMPLETE == status && TaskStatusConst.INIT == taskRecordEntity.getStatus()) {
            // 检测一下任务是否需要审核，若需要，则不能直接设置为完成，需要设置为待审核
            TaskEntity taskEntity = taskDao.selectById(taskRecordEntity.getTaskId());
            if (taskEntity.getVerifyFlag()) {
                status = TaskStatusConst.WAIT_VERIFY;
            }
        }

        TaskRecordEntity newTaskRecordEntity = new TaskRecordEntity();
        newTaskRecordEntity.setId(id);
        newTaskRecordEntity.setStatus(status);
        taskRecordDao.updateById(newTaskRecordEntity);
        return ResponseDTO.ok(newTaskRecordEntity.getStatus());
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
            return this.updateTaskStatus(selectOne.getId(), taskRecordCompleteForm.getStatus());
        } else {

            TaskRecordEntity taskRecordEntity = SmartBeanUtil.copy(taskRecordCompleteForm, TaskRecordEntity.class);
            taskRecordEntity.setStatus(TaskStatusConst.INIT);
            taskRecordDao.insert(taskRecordEntity);
            return this.updateTaskStatus(taskRecordEntity.getId(), taskRecordCompleteForm.getStatus());
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
