package net.tunie.sf.module.login.service;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.common.utils.SmartUserUtil;
import net.tunie.sf.module.login.domain.LoginForm;
import net.tunie.sf.module.login.domain.LoginResultVo;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.user.domain.entity.UserEntity;
import net.tunie.sf.module.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Resource
    private UserService userService;

    public ResponseDTO<LoginResultVo> login(LoginForm loginForm) {
        if (null == loginForm.getPassword()) {
            return ResponseDTO.userErrorParams("用户密码不存在");
        }

        UserEntity userEntity = userService.getUserByNameOrMobile(loginForm.getUserName(), loginForm.getUserName());
        if (null == userEntity) {
            return ResponseDTO.userErrorParams("用户不存在");
        }

        if (!userEntity.getPassword().equals(loginForm.getPassword())) {
            return ResponseDTO.userErrorParams("密码错误");
        }

        StpUtil.login(userEntity.getId());

        String tokenValue = StpUtil.getTokenValue();

        RequestUser loginUser = getLoginUser(userEntity);
        LoginResultVo loginResultVo = SmartBeanUtil.copy(loginUser, LoginResultVo.class);
        loginResultVo.setToken(tokenValue);

        return ResponseDTO.ok(loginResultVo);
    }

    public ResponseDTO<String> logout() {
        return ResponseDTO.ok();
    }

    public RequestUser getLoginUser(String userId) {
        UserEntity userEntity = userService.getUserById(userId);
        return getLoginUser(userEntity);
    }

    public RequestUser getLoginUser(UserEntity userEntity) {
        RequestUser requestUser = new RequestUser();
        requestUser.setUserId(userEntity.getId());
        requestUser.setParentId(userEntity.getParentId());
        requestUser.setUserName(userEntity.getNickname());
        requestUser.setType(SmartUserUtil.getUserType(userEntity));
        return requestUser;
    }
}
