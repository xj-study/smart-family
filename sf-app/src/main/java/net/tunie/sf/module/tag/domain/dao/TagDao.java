package net.tunie.sf.module.tag.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.tag.domain.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface TagDao extends BaseMapper<TagEntity> {
}
