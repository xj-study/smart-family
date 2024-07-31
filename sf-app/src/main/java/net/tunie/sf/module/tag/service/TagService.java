package net.tunie.sf.module.tag.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.tag.domain.dao.TagDao;
import net.tunie.sf.module.tag.domain.entity.TagEntity;
import net.tunie.sf.module.tag.domain.form.TagAddForm;
import net.tunie.sf.module.tag.domain.form.TagUpdateForm;
import net.tunie.sf.module.tag.domain.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService extends ServiceImpl<TagDao, TagEntity> {

    public ResponseDTO<String> updateTag(TagUpdateForm tagUpdateForm) {
        TagEntity byId = this.getById(tagUpdateForm.getId());
        if (byId == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        TagEntity byName = this.getByName(tagUpdateForm.getName(), tagUpdateForm.getUserId());
        if (byName != null) {
            return ResponseDTO.userErrorParams("对应的标签已经存在了，无法更新");
        }

        this.updateById(SmartBeanUtil.copy(tagUpdateForm, TagEntity.class));
        return ResponseDTO.ok();
    }

    public ResponseDTO<List<TagVo>> queryTag(Long requestUserId, String keyword) {
        List<TagEntity> list = this.list(Wrappers.lambdaQuery(TagEntity.class).eq(TagEntity::getUserId, requestUserId).like(TagEntity::getName, keyword));
        List<TagVo> tagVos = SmartBeanUtil.copyList(list, TagVo.class);
        return ResponseDTO.ok(tagVos);
    }

    public ResponseDTO<Long> addTag(TagAddForm tagAddForm) {
        TagEntity byName = this.getByName(tagAddForm.getName(), tagAddForm.getUserId());
        if (byName != null) {
            return ResponseDTO.userErrorParams("对应的标签已经存在了，不需要重新添加");
        }
        TagEntity copy = SmartBeanUtil.copy(tagAddForm, TagEntity.class);
        this.save(copy);
        return ResponseDTO.ok(copy.getId());
    }

    public TagEntity getByName(String name, Long userId) {
        return this.getOne(Wrappers.lambdaQuery(TagEntity.class).eq(TagEntity::getUserId, userId).eq(TagEntity::getName, name));
    }

    public ResponseDTO<String> deleteTag(Long requestUserId, Long id) {
        TagEntity byId = this.getById(id);
        if (byId == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        if (!byId.getUserId().equals(requestUserId)) {
            return ResponseDTO.error(UserErrorCode.NO_PERMISSION, "你没有权限删除别人的标签");
        }
        this.removeById(id);
        return ResponseDTO.ok();
    }
}
