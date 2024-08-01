package net.tunie.sf.module.tag.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.tunie.sf.module.tag.domain.dao.TagRefDao;
import net.tunie.sf.module.tag.domain.entity.TagRefEntity;
import net.tunie.sf.module.tag.domain.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagRefService extends ServiceImpl<TagRefDao, TagRefEntity> {
    public List<TagVo> getTags(Long refId, int refType) {
        return this.baseMapper.getTags(refId, refType);
    }

    public List<TagVo> getUserTags(Long userId, int refType) {
        return this.baseMapper.getUserTags(userId, refType);
    }
    
    public void saveBatch(List<Long> tagIds, Long refId, int refType) {
        if(tagIds.isEmpty()) return;
        List<TagRefEntity> list = tagIds.stream().map(tagId -> {
            TagRefEntity tagRefEntity = new TagRefEntity();
            tagRefEntity.setRefId(refId);
            tagRefEntity.setRefType(refType);
            tagRefEntity.setTagId(tagId);
            return tagRefEntity;
        }).toList();
        this.saveBatch(list);
    }

    public void removeTag(Long refId, int refType) {
        this.baseMapper.removeTag(refId, refType);
    }
}
