package net.tunie.sf.module.gift.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.module.gift.domain.form.GiftAddForm;
import net.tunie.sf.module.gift.domain.form.GiftUpdateForm;
import net.tunie.sf.module.gift.domain.vo.GiftVo;
import net.tunie.sf.module.gift.service.GiftService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GiftController {

    @Resource
    private GiftService giftService;

    @PostMapping("/gift/add")
    public ResponseDTO<String> add(@RequestBody GiftAddForm giftAddForm) {
        Long requestUserId = SmartRequestUtil.getRequestUserId();
        giftAddForm.setUserId(requestUserId);
        return giftService.addGift(giftAddForm);
    }

    @GetMapping("/gift/{id}/query")
    public ResponseDTO<GiftVo> queryDetail(@PathVariable long id) {
        return giftService.queryGiftDetail(id);
    }

    @GetMapping("/gift/query")
    public ResponseDTO<List<GiftVo>> query() {
        Long userId = SmartRequestUtil.getUserParentId();
        return giftService.queryGift(userId);
    }

    @PostMapping("/gift/update")
    public ResponseDTO<String> update(@RequestBody GiftUpdateForm giftUpdateForm) {
        Long requestUserId = SmartRequestUtil.getRequestUserId();
        giftUpdateForm.setUserId(requestUserId);
        return giftService.updateGift(giftUpdateForm);
    }


}
