package net.tunie.sf.module.gift.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class GiftService extends ServiceImpl<GiftDao, GiftEntity> {

    public ResponseDTO<Long> addGift(GiftAddForm giftAddForm) {

        GiftEntity giftEntity = SmartBeanUtil.copy(giftAddForm, GiftEntity.class);
        this.save(giftEntity);

        return ResponseDTO.ok(giftEntity.getId());
    }

    public ResponseDTO<List<GiftVo>> queryGift(Long userId) {
        LambdaQueryWrapper<GiftEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(GiftEntity::getUserId, userId);
        List<GiftEntity> giftEntities = this.list(lambdaQueryWrapper);
        List<GiftVo> giftVos = SmartBeanUtil.copyList(giftEntities, GiftVo.class);
        return ResponseDTO.ok(giftVos);
    }

    public ResponseDTO<GiftVo> queryGiftDetail(Long giftId) {
        GiftEntity giftEntity = this.getById(giftId);
        if (null == giftEntity) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        GiftVo giftVo = SmartBeanUtil.copy(giftEntity, GiftVo.class);
        return ResponseDTO.ok(giftVo);
    }

    public ResponseDTO<String> updateGift(GiftUpdateForm giftUpdateForm) {
        GiftEntity giftEntity = this.getById(giftUpdateForm.getId());
        if (giftEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        GiftEntity updateGiftEntity = SmartBeanUtil.copy(giftUpdateForm, GiftEntity.class);
        this.updateById(updateGiftEntity);

        return ResponseDTO.ok();
    }

}