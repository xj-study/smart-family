package net.tunie.sf.module.task.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.common.utils.SmartUserUtil;
import net.tunie.sf.constant.TagTypeConst;
import net.tunie.sf.constant.TaskStatusConst;
import net.tunie.sf.constant.RecordTypeConst;
import net.tunie.sf.constant.RuleTypeConst;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.tag.domain.vo.TagVo;
import net.tunie.sf.module.tag.service.TagRefService;
import net.tunie.sf.module.task.constant.TaskDateTypeQueryConst;
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
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskRecordService extends ServiceImpl<TaskRecordDao, TaskRecordEntity> {

    @Resource
    private TaskService taskService;

    @Resource
    private TaskIntegralService taskIntegralService;

    @Resource
    private UserIntegralService userIntegralService;

    @Resource
    private TagRefService tagRefService;

    private LocalDate getTaskDate(Integer dateType) {
        LocalDate taskDate = LocalDate.now();
        // 处理 task date
        if (dateType == TaskDateTypeQueryConst.YESTODAY) {
            //taskDate
            taskDate = taskDate.minusDays(1);
        }
        return taskDate;
    }

    public ResponseDTO<List<TaskRecordVo>> queryDailyTaskRecord(
            RequestUser requestUser, TaskRecordQueryForm taskRecordQueryForm) {

        Long taskUserId = requestUser.getUserId();
        if (SmartUserUtil.getUserChildFlag(requestUser.getType())) {
            taskUserId = requestUser.getParentId();
        }

        Long recordUserId = requestUser.getUserId();
        if (taskRecordQueryForm.getId() != null) {
            recordUserId = taskRecordQueryForm.getId();
        }

        LocalDate taskDate = this.getTaskDate(taskRecordQueryForm.getDate());

        String keyword = taskRecordQueryForm.getKeyword();
        if (Strings.isNotBlank(keyword)) {
            keyword = "%" + keyword + "%";
        }
        List<TaskRecordVo> taskRecordVos = this.baseMapper.queryDailyTaskRecord(
                taskUserId, recordUserId, taskDate, taskRecordQueryForm.getStatus(), keyword, taskRecordQueryForm.getTagId(), TagTypeConst.TASK);

        // 查询关联的tag数据
        taskRecordVos.forEach(item -> {
            List<TagVo> tags = tagRefService.getTags(item.getTaskId(), TagTypeConst.TASK);
            if (tags.isEmpty()) return;
            item.setTag(tags.stream().map(TagVo::getId).toList());
            item.setTagStr(Strings.join(tags.stream().map(TagVo::getName).toList(), ','));
        });

        return ResponseDTO.ok(taskRecordVos);
    }

    // 检查一下任务更新的状态
    private Integer getTaskUpdateStatus(TaskRecordEntity taskRecordEntity, TaskEntity taskEntity, Integer status) {
        if (taskEntity.getTaskType() == RuleTypeConst.COMMON) {
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

        TaskEntity taskEntity = this.taskService.getById(taskRecordEntity.getTaskId());
        status = getTaskUpdateStatus(taskRecordEntity, taskEntity, status);

        taskRecordEntity.setStatus(status);
        this.updateById(taskRecordEntity);

        // 更新积分
        if (TaskStatusConst.COMPLETE == status) {

            TaskIntegralEntity taskIntegralEntity = this.taskIntegralService.getById(taskRecordEntity.getTaskId());

            UserIntegralUpdateForm userIntegralUpdateForm = new UserIntegralUpdateForm();
            userIntegralUpdateForm.setIntegralChange(taskIntegralEntity.getIntegral());
            userIntegralUpdateForm.setUserId(taskRecordEntity.getUserId());
            userIntegralUpdateForm.setRefType(RecordTypeConst.TASK);
            userIntegralUpdateForm.setRefId(taskRecordEntity.getId());

            TaskJsonVo taskJsonVo = SmartBeanUtil.copy(taskEntity, TaskJsonVo.class);
            taskJsonVo.setTaskDate(taskRecordEntity.getTaskDate());
            taskJsonVo.setId(taskRecordEntity.getId());
            taskJsonVo.setTaskId(taskEntity.getId());
            String content = JSON.toJSONString(taskJsonVo);

            userIntegralUpdateForm.setContent(content);

            userIntegralService.update(userIntegralUpdateForm);
        }

        return ResponseDTO.ok(status);
    }

    public ResponseDTO<Integer> updateTaskStatus(Long id, Integer status) {
        TaskRecordEntity taskRecordEntity = this.getById(id);
        return this.updateTaskStatus(taskRecordEntity, status);
    }

    public ResponseDTO<Integer> updateTaskStatus(TaskRecordCompleteForm taskRecordCompleteForm) {

        LocalDate taskDate = this.getTaskDate(taskRecordCompleteForm.getDate());
        TaskRecordEntity queryTaskRecordEntity = new TaskRecordEntity();
        queryTaskRecordEntity.setTaskId(taskRecordCompleteForm.getTaskId());
        queryTaskRecordEntity.setUserId(taskRecordCompleteForm.getUserId());
        queryTaskRecordEntity.setTaskDate(taskDate);

        QueryWrapper<TaskRecordEntity> query = Wrappers.query(queryTaskRecordEntity);

        TaskRecordEntity selectOne = this.getOne(query);
        if (selectOne != null) {
            return this.updateTaskStatus(selectOne, taskRecordCompleteForm.getStatus());
        } else {

            TaskRecordEntity taskRecordEntity = SmartBeanUtil.copy(taskRecordCompleteForm, TaskRecordEntity.class);
            taskRecordEntity.setTaskDate(taskDate);
            taskRecordEntity.setStatus(TaskStatusConst.INIT);
            this.save(taskRecordEntity);
            return this.updateTaskStatus(taskRecordEntity, taskRecordCompleteForm.getStatus());
        }

    }

    public ResponseDTO<String> verify(RequestUser user, Long id) {
        // 没权限
        if (SmartUserUtil.getUserChildFlag(user.getType())) {
            return ResponseDTO.error(UserErrorCode.NO_PERMISSION);
        }

        // 数据不存在
        TaskRecordEntity taskRecordEntity = this.getById(id);
        if (taskRecordEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        // 不满足审核状态
        if (taskRecordEntity.getStatus() != TaskStatusConst.WAIT_VERIFY) {
            return ResponseDTO.userErrorParams("任务状态不满足审核条件");
        }

        // 审核通过更新
        taskRecordEntity.setStatus(TaskStatusConst.COMPLETE);
        this.updateById(taskRecordEntity);

        return ResponseDTO.ok();
    }

    public ResponseDTO<List<TagVo>> queryTag(Long userId) {
        List<TagVo> userTags = this.tagRefService.getUserTags(userId, TagTypeConst.TASK);
        return ResponseDTO.ok(userTags);
    }
}
