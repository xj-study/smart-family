package net.tunie.sf.module.story.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.service.RulesService;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.story.domain.dao.StoryLevelDao;
import net.tunie.sf.module.story.domain.entity.StoryLevelEntity;
import net.tunie.sf.module.story.domain.form.StoryLevelAddForm;
import net.tunie.sf.module.story.domain.form.StoryLevelUpdateForm;
import net.tunie.sf.module.story.domain.vo.StoryLevelVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryLevelService extends ServiceImpl<StoryLevelDao, StoryLevelEntity> implements RulesService {

    public List<StoryLevelEntity> getStoryLevelByOrder(StoryLevelEntity storyLevelEntity) {
        if (storyLevelEntity == null) return null;
        if (storyLevelEntity.getLevelOrder() == -1) return null;
        LambdaQueryWrapper<StoryLevelEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StoryLevelEntity::getStoryId, storyLevelEntity.getStoryId());
        lambdaQueryWrapper.eq(StoryLevelEntity::getLevelOrder, storyLevelEntity.getLevelOrder());
        lambdaQueryWrapper.ne(storyLevelEntity.getId() != null, StoryLevelEntity::getId, storyLevelEntity.getId());

        return this.list(lambdaQueryWrapper);
    }

    public void updateStoreLevelOrder(StoryLevelEntity storyLevelEntity) {
        List<StoryLevelEntity> storyLevelByOrder = this.getStoryLevelByOrder(storyLevelEntity);
        if (storyLevelByOrder == null) {
            return;
        }
        storyLevelByOrder.forEach(item -> {
            item.setLevelOrder(0);
            this.updateById(item);
        });

    }

    public ResponseDTO<Long> addStoryLevel(StoryLevelAddForm storyLevelAddForm) {
        StoryLevelEntity storyLevelEntity = SmartBeanUtil.copy(storyLevelAddForm, StoryLevelEntity.class);

        this.updateStoreLevelOrder(storyLevelEntity);

        this.save(storyLevelEntity);

        return ResponseDTO.ok(storyLevelEntity.getId());
    }

    public ResponseDTO<String> updateStoryLevel(StoryLevelUpdateForm storyLevelUpdateForm) {
        StoryLevelEntity storyLevelEntity = this.getById(storyLevelUpdateForm.getId());
        if (storyLevelEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        storyLevelEntity = SmartBeanUtil.copy(storyLevelUpdateForm, StoryLevelEntity.class);

        this.updateStoreLevelOrder(storyLevelEntity);
        this.updateById(storyLevelEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<List<StoryLevelVo>> queryStoryLevelList(Long storyId, long size) {
        LambdaQueryWrapper<StoryLevelEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StoryLevelEntity::getStoryId, storyId);
        lambdaQueryWrapper.orderByAsc(StoryLevelEntity::getLevelOrder);
        List<StoryLevelEntity> storyLevelEntities = null;
        if (size >= 0) {
            storyLevelEntities = this.list(new Page<>(1, size), lambdaQueryWrapper);
            //storyLevelEntities = this.page(new Page<>(1, size), lambdaQueryWrapper).getRecords();
        } else {
            storyLevelEntities = this.list(lambdaQueryWrapper);
        }
        List<StoryLevelVo> storyLevelVos = SmartBeanUtil.copyList(storyLevelEntities, StoryLevelVo.class);
        return ResponseDTO.ok(storyLevelVos);
    }

    public ResponseDTO<List<StoryLevelVo>> queryStoryLevelList(Long storyId) {
        return this.queryStoryLevelList(storyId, -1);
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

    @Override
    public JSONObject getRules(Long id) {
        return null;
    }
}
