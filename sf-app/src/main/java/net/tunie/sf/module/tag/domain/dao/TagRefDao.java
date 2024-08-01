package net.tunie.sf.module.tag.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.tunie.sf.module.tag.domain.entity.TagRefEntity;
import net.tunie.sf.module.tag.domain.vo.TagVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TagRefDao extends BaseMapper<TagRefEntity> {
    List<TagVo> getTags(@Param("refId") Long refId, @Param("refType") int refType);

    void removeTag(@Param("refId") Long refId, @Param("refType") int refType);
}
