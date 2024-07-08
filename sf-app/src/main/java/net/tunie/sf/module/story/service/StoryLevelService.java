package net.tunie.sf.module.story.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.story.domain.dao.StoryLevelDao;
import net.tunie.sf.module.story.domain.entity.StoryEntity;
import net.tunie.sf.module.story.domain.entity.StoryLevelEntity;
import net.tunie.sf.module.story.domain.form.*;
import net.tunie.sf.module.story.domain.vo.StoryLevelVo;
import net.tunie.sf.module.story.domain.vo.StoryVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryLevelService extends ServiceImpl<StoryLevelDao, StoryLevelEntity> {


    public ResponseDTO<Long> addStoryLevel(StoryLevelAddForm storyLevelAddForm) {
        StoryLevelEntity storyLevelEntity = SmartBeanUtil.copy(storyLevelAddForm, StoryLevelEntity.class);
        this.save(storyLevelEntity);

        return ResponseDTO.ok(storyLevelEntity.getId());
    }

    public ResponseDTO<String> updateStoryLevel(StoryLevelUpdateForm storyLevelUpdateForm) {
        StoryLevelEntity storyLevelEntity = this.getById(storyLevelUpdateForm.getId());
        if (storyLevelEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        storyLevelEntity = SmartBeanUtil.copy(storyLevelUpdateForm, StoryLevelEntity.class);
        this.updateById(storyLevelEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<List<StoryLevelVo>> queryStoryLevelList(Long storyId) {
        LambdaQueryWrapper<StoryLevelEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StoryLevelEntity::getStoryId, storyId);
        List<StoryLevelEntity> storyLevelEntities = this.list(lambdaQueryWrapper);
        List<StoryLevelVo> storyLevelVos = SmartBeanUtil.copyList(storyLevelEntities, StoryLevelVo.class);
        return ResponseDTO.ok(storyLevelVos);
    }

    public ResponseDTO<StoryLevelVo> queryStoryLevel(Long storyLevelId) {
        StoryLevelEntity storyLevelEntity = this.getById(storyLevelId);
        if (storyLevelEntity == null) {
            return ResponseDTO.ok(null);
        }
        StoryLevelVo storyLevelVo = SmartBeanUtil.copy(storyLevelEntity, StoryLevelVo.class);
        return ResponseDTO.ok(storyLevelVo);
    }

    public ResponseDTO<String> updateStoryLevelDisable(Long storyLevelId) {
        StoryLevelEntity storyLevelEntity = this.getById(storyLevelId);
        if (storyLevelEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        this.removeById(storyLevelId);
        return ResponseDTO.ok();
    }
}
