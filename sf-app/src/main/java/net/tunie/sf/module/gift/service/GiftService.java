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

    public ResponseDTO<String> addGift(GiftAddForm giftAddForm) {

        GiftEntity giftEntity = SmartBeanUtil.copy(giftAddForm, GiftEntity.class);
        giftDao.insert(giftEntity);

        return ResponseDTO.ok();
    }

    public GiftEntity selectGiftById(Long giftId) {
        return giftDao.selectById(giftId);
    }

    public ResponseDTO<List<GiftVo>> queryGift(Long userId) {
        QueryWrapper<GiftEntity> giftEntityQueryWrapper = Wrappers.query();
        giftEntityQueryWrapper.eq("user_id", userId);
        List<GiftEntity> giftEntities = giftDao.selectList(giftEntityQueryWrapper);
        List<GiftVo> giftVos = SmartBeanUtil.copyList(giftEntities, GiftVo.class);
        return ResponseDTO.ok(giftVos);
    }

    public ResponseDTO<GiftVo> queryGiftDetail(Long giftId) {
        GiftEntity giftEntity = this.selectGiftById(giftId);
        if (null == giftEntity) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        GiftVo giftVo = SmartBeanUtil.copy(giftEntity, GiftVo.class);
        return ResponseDTO.ok(giftVo);
    }

    public ResponseDTO<String> updateGift(GiftUpdateForm giftUpdateForm) {
        GiftEntity giftEntity = giftDao.selectById(giftUpdateForm.getGiftId());
        if (giftEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        GiftEntity updateGiftEntity = SmartBeanUtil.copy(giftUpdateForm, GiftEntity.class);
        giftDao.updateById(updateGiftEntity);

        return ResponseDTO.ok();
    }

}