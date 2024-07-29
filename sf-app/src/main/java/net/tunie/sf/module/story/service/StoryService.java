package net.tunie.sf.module.story.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.story.domain.dao.StoryDao;
import net.tunie.sf.module.story.domain.entity.StoryEntity;
import net.tunie.sf.module.story.domain.form.StoryAddForm;
import net.tunie.sf.module.story.domain.form.StoryQueryForm;
import net.tunie.sf.module.story.domain.form.StoryUpdateForm;
import net.tunie.sf.module.story.domain.vo.StoryVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService extends ServiceImpl<StoryDao, StoryEntity> {

    public ResponseDTO<Long> addStory(StoryAddForm storyAddForm) {
        StoryEntity storyEntity = SmartBeanUtil.copy(storyAddForm, StoryEntity.class);
        this.save(storyEntity);

        return ResponseDTO.ok(storyEntity.getId());
    }

    public ResponseDTO<Integer> updateStory(StoryUpdateForm storyUpdateForm) {
        StoryEntity storyEntity = this.getById(storyUpdateForm.getId());
        if (storyEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        storyEntity = SmartBeanUtil.copy(storyUpdateForm, StoryEntity.class);
        this.updateById(storyEntity);
        return ResponseDTO.ok(storyEntity.getStatus());
    }

    public ResponseDTO<List<StoryVo>> queryStoryList(StoryQueryForm storyQueryForm) {
        LambdaQueryWrapper<StoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StoryEntity::getUserId, storyQueryForm.getUserId());
        if (storyQueryForm.getStatus() != null) {
            lambdaQueryWrapper.eq(StoryEntity::getStatus, storyQueryForm.getStatus());
        }
        lambdaQueryWrapper.orderByDesc(StoryEntity::getUpdateTime);
        List<StoryEntity> storyEntities = this.list(lambdaQueryWrapper);
        List<StoryVo> storyVos = SmartBeanUtil.copyList(storyEntities, StoryVo.class);
        return ResponseDTO.ok(storyVos);
    }

    public ResponseDTO<StoryVo> queryStory(Long storyId) {
        StoryEntity storyEntity = this.getById(storyId);
        if (storyEntity == null) {
            return ResponseDTO.ok(null);
        }
        StoryVo storyVo = SmartBeanUtil.copy(storyEntity, StoryVo.class);
        return ResponseDTO.ok(storyVo);
    }
}
