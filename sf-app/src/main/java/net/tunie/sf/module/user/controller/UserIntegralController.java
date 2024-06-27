package net.tunie.sf.module.user.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.common.utils.SmartUserUtil;
import net.tunie.sf.module.user.service.UserIntegralService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserIntegralController {

    @Resource
    private UserIntegralService userIntegralService;


    @GetMapping("/user/integral/query")
    public ResponseDTO<Integer> queryIntegral() {
         return userIntegralService.queryIntegral(SmartRequestUtil.getRequestUserId());
    }
}
