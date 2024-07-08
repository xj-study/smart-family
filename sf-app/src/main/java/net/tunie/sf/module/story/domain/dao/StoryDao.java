package net.tunie.sf.module.story.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.story.domain.entity.StoryEntity;
import net.tunie.sf.module.story.domain.form.StoryQueryForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface StoryDao extends BaseMapper<StoryEntity> {

    List<StoryEntity> queryStoryList(@Param("storyQueryForm") StoryQueryForm storyQueryForm);
}
