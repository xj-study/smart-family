package net.tunie.sf.module.user.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.PageDTO;
import net.tunie.sf.common.domain.PageParam;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.module.user.domain.vo.UserIntegralRecordVo;
import net.tunie.sf.module.user.service.UserIntegralRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserIntegralRecordController {

    @Resource
    private UserIntegralRecordService userIntegralRecordService;

    @PostMapping("/user/integral/record/query")
    public ResponseDTO<PageDTO<UserIntegralRecordVo>> query(@RequestBody PageParam pageParam) {
        Long requestUserId = SmartRequestUtil.getRequestUserId();
        return userIntegralRecordService.queryRecord(requestUserId, pageParam);
    }
}
