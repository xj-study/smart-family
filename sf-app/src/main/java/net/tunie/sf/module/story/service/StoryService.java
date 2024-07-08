package net.tunie.sf.module.story.service;

import jakarta.annotation.Resource;
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
public class StoryService {

    @Resource
    private StoryDao storyDao;


    public ResponseDTO<Long> addStory(StoryAddForm storyAddForm) {
        StoryEntity storyEntity = SmartBeanUtil.copy(storyAddForm, StoryEntity.class);
        storyDao.insert(storyEntity);

        return ResponseDTO.ok(storyEntity.getId());
    }

    public ResponseDTO<String> updateStory(StoryUpdateForm storyUpdateForm) {
        StoryEntity storyEntity = storyDao.selectById(storyUpdateForm.getId());
        if (storyEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        storyEntity = SmartBeanUtil.copy(storyUpdateForm, StoryEntity.class);
        storyDao.updateById(storyEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<List<StoryVo>> queryStoryList(StoryQueryForm storyQueryForm) {
        List<StoryEntity> storyEntities = storyDao.queryStoryList(storyQueryForm);
        List<StoryVo> storyVos = SmartBeanUtil.copyList(storyEntities, StoryVo.class);
        return ResponseDTO.ok(storyVos);
    }

    public ResponseDTO<StoryVo> queryStory(Long storyId) {
        StoryEntity storyEntity = storyDao.selectById(storyId);
        if (storyEntity == null) {
            return ResponseDTO.ok(null);
        }
        StoryVo storyVo = SmartBeanUtil.copy(storyEntity, StoryVo.class);
        return ResponseDTO.ok(storyVo);
    }

    public ResponseDTO<String> updateStoryDisable(Long storyId) {
        StoryEntity storyEntity = storyDao.selectById(storyId);
        if (storyEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        storyEntity.setDisableFlag(true);
        storyDao.updateById(storyEntity);
        return ResponseDTO.ok();
    }
}
