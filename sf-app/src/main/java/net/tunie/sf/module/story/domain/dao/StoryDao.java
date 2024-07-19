package net.tunie.sf.module.story.domain.dao;

import net.tunie.sf.injector.mybatisplus.MyBaseMapper;
import net.tunie.sf.module.story.domain.entity.StoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface StoryDao extends MyBaseMapper<StoryEntity> {

}
