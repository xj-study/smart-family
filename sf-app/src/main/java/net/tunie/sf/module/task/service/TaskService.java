package net.tunie.sf.module.task.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.service.RulesService;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.constant.RuleTypeConst;
import net.tunie.sf.constant.TagTypeConst;
import net.tunie.sf.module.ques.constant.QuesTypeConst;
import net.tunie.sf.module.ques.domain.form.QuesQueryForm;
import net.tunie.sf.module.ques.service.QuesService;
import net.tunie.sf.module.tag.domain.entity.TagRefEntity;
import net.tunie.sf.module.tag.domain.vo.TagVo;
import net.tunie.sf.module.tag.service.TagRefService;
import net.tunie.sf.module.task.domain.dao.TaskDao;
import net.tunie.sf.module.task.domain.entity.TaskEntity;
import net.tunie.sf.module.task.domain.entity.TaskIntegralEntity;
import net.tunie.sf.module.task.domain.form.TaskAddForm;
import net.tunie.sf.module.task.domain.form.TaskUpdateForm;
import net.tunie.sf.module.task.domain.vo.TaskVo;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService extends ServiceImpl<TaskDao, TaskEntity> implements RulesService<TaskEntity> {

    @Resource
    private TaskIntegralService taskIntegralService;

    @Resource
    private TagRefService tagRefService;
    @Resource
    private QuesService quesService;

    public ResponseDTO<Long> addTask(TaskAddForm taskAddForm) {
        TaskEntity taskEntity = SmartBeanUtil.copy(taskAddForm, TaskEntity.class);
        this.save(taskEntity);

        // 积分
        TaskIntegralEntity taskIntegralEntity = new TaskIntegralEntity();
        taskIntegralEntity.setTaskId(taskEntity.getId());
        taskIntegralEntity.setIntegral(taskAddForm.getIntegral());
        taskIntegralService.save(taskIntegralEntity);

        // 标签
        this.tagRefService.saveBatch(taskAddForm.getTag(), taskEntity.getId(), TagTypeConst.TASK);

        this.addOrUpdateQuesByRule(taskEntity);

        return ResponseDTO.ok(taskEntity.getId());
    }

    public ResponseDTO<List<TaskVo>> queryTask(Long requestUserId, String keyword) {
        if (Strings.isNotBlank(keyword)) {
            keyword = '%' + keyword + '%';
        } else {
            keyword = null;
        }
        List<TaskVo> taskVos = this.baseMapper.getTaskList(requestUserId, keyword);

        // 查询关联的tag数据
        taskVos.forEach(taskVo -> {
            List<TagVo> tags = tagRefService.getTags(taskVo.getTaskId(), TagTypeConst.TASK);
            if (tags.isEmpty()) return;
            taskVo.setTag(tags.stream().map(TagVo::getId).toList());
            taskVo.setTagStr(Strings.join(tags.stream().map(TagVo::getName).toList(), ','));
        });

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

        // 更新标签前先删除原来关联的标签
        this.tagRefService.removeTag(updateTaskEntity.getId(), TagTypeConst.TASK);
        // 标签
        this.tagRefService.saveBatch(taskUpdateForm.getTag(), updateTaskEntity.getId(), TagTypeConst.TASK);

        this.addOrUpdateQuesByRule(updateTaskEntity);

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
    public JSONObject getRules(TaskEntity taskEntity) {
        if (taskEntity.getRules() != null) {
            return JSON.parseObject(taskEntity.getRules());
        }
        return null;
    }

    @Override
    public JSONObject getRules(Long id) {
        TaskEntity entity = this.getById(id);
        return getRules(entity);
    }

    @Override
    public void addOrUpdateQuesByRule(TaskEntity taskEntity) {
        if (RuleTypeConst.needQuesUpdate(taskEntity.getTaskType())) {
            QuesQueryForm quesQueryForm = new QuesQueryForm();
            quesQueryForm.setId(taskEntity.getId());
            quesQueryForm.setType(QuesTypeConst.TASK);
            quesQueryForm.setRules(taskEntity.getRules());

            quesService.addOrUpdateQues(quesQueryForm);
        }
    }
}
