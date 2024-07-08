package net.tunie.sf.module.user.controller;

import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.common.utils.SmartRequestUtil;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.login.service.LoginService;
import net.tunie.sf.module.user.domain.entity.UserEntity;
import net.tunie.sf.module.user.domain.form.UserAddForm;
import net.tunie.sf.module.user.domain.form.UserUpdateForm;
import net.tunie.sf.module.user.domain.vo.UserVo;
import net.tunie.sf.module.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private LoginService loginService;

    //@PostMapping("/user/add")
    //public ResponseDTO<String> add(@RequestBody UserAddForm userAddForm){
    //    return userService.addUser(userAddForm);
    //}
    //
    //
    //@GetMapping("/user/bind/{parentId}")
    //public ResponseDTO<String> bindParent(@PathVariable Long parentId) {
    //    Long requestUserId = SmartRequestUtil.getRequestUserId();
    //    return userService.bindParent(requestUserId, parentId);
    //}

    //@GetMapping("/user/{id}/query")
    //public ResponseDTO<UserVo> queryDetail(@PathVariable Long id) {
    //    return userService.queryDetail(id);
    //}

    @GetMapping("/user/query")
    public ResponseDTO<RequestUser> query() {
        Long requestUserId = SmartRequestUtil.getRequestUserId();
        return loginService.queryLoginUser(requestUserId);
    }

    @PostMapping("/user/update")
    public ResponseDTO<String> update(@RequestBody UserUpdateForm userUpdateForm) {
        Long requestUserId = SmartRequestUtil.getRequestUserId();
        userUpdateForm.setId(requestUserId);

        return userService.updateUser(userUpdateForm);
    }

    @GetMapping("/user/children/query")
    public ResponseDTO<List<UserVo>> queryChildren() {
        Long requestUserId = SmartRequestUtil.getRequestUserId();
        return userService.getUserChildren(requestUserId);
    }
}
