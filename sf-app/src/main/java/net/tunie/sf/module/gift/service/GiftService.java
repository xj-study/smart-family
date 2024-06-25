package net.tunie.sf.module.gift.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.gift.domain.dao.GiftDao;
import net.tunie.sf.module.gift.domain.entity.GiftEntity;
import net.tunie.sf.module.gift.domain.form.GiftAddForm;
import net.tunie.sf.module.gift.domain.form.GiftUpdateForm;
import net.tunie.sf.module.gift.domain.vo.GiftVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftService {
    @Resource
    private GiftDao giftDao;

    public ResponseDTO<String> addGiftUser(GiftAddForm giftAddForm) {

        GiftEntity giftUserEntity = SmartBeanUtil.copy(giftAddForm, GiftEntity.class);
        giftDao.insert(giftUserEntity);

        return ResponseDTO.ok();
    }

    public ResponseDTO<List<GiftVo>> queryGiftUser(Long userId) {
        QueryWrapper<GiftEntity> giftUserEntityQueryWrapper = Wrappers.query();
        giftUserEntityQueryWrapper.eq("user_id", userId);
        List<GiftEntity> giftUserEntities = giftDao.selectList(giftUserEntityQueryWrapper);
        List<GiftVo> giftUserVos = SmartBeanUtil.copyList(giftUserEntities, GiftVo.class);
        return ResponseDTO.ok(giftUserVos);
    }

    public ResponseDTO<GiftVo> queryGiftUserDetail(Long giftId) {
        GiftEntity giftUserEntity = giftDao.selectById(giftId);
        if (null == giftUserEntity) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        GiftVo giftUserVo = SmartBeanUtil.copy(giftUserEntity, GiftVo.class);
        return ResponseDTO.ok(giftUserVo);
    }

    public ResponseDTO<String> updateGiftUser(GiftUpdateForm giftUpdateForm) {
        GiftEntity giftUserEntity = giftDao.selectById(giftUpdateForm.getGiftId());
        if (giftUserEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        GiftEntity updateGiftUserEntity = SmartBeanUtil.copy(giftUpdateForm, GiftEntity.class);
        giftDao.updateById(updateGiftUserEntity);

        return ResponseDTO.ok();
    }

}