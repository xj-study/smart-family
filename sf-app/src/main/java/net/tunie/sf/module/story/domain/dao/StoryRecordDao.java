package net.tunie.sf.module.story.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.story.domain.entity.StoryRecordEntity;
import net.tunie.sf.module.story.domain.form.StoryRecordQueryForm;
import net.tunie.sf.module.story.domain.vo.StoryRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoryRecordDao extends BaseMapper<StoryRecordEntity> {
    List<StoryRecordVo> selectStoryRecordList(@Param("parentId") Long parentId, @Param("userId")  Long userId, @Param("query") StoryRecordQueryForm storyRecordQueryForm);

    StoryRecordVo selectStoryRecord(@Param("userId") Long userId, @Param("storyId") Long storyId);
}
