package net.tunie.sf.module.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.login.domain.RegisterForm;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.login.service.LoginService;
import net.tunie.sf.module.user.domain.dao.UserDao;
import net.tunie.sf.module.user.domain.entity.UserEntity;
import net.tunie.sf.module.user.domain.form.UserAddChildForm;
import net.tunie.sf.module.user.domain.form.UserAddForm;
import net.tunie.sf.module.user.domain.form.UserUpdateForm;
import net.tunie.sf.module.user.domain.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserDao, UserEntity> {

    public ResponseDTO<String> addUser(RegisterForm registerForm) {
        UserEntity userEntity = this.getUserByNameOrMobile(registerForm.getName(), registerForm.getName());

        if (userEntity != null) {
            return ResponseDTO.userErrorParams("用户名或手机号已经注册了！");
        }

        if(!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            return ResponseDTO.userErrorParams("密码不一致！");
        }

        UserEntity newUserEntity = SmartBeanUtil.copy(registerForm, UserEntity.class);
        newUserEntity.setNickname(registerForm.getName());
        this.save(newUserEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<UserVo> addChild(UserAddChildForm userAddChildForm) {
        UserEntity userEntity = this.getUserByNameOrMobile(userAddChildForm.getName(), "");
        if (userEntity != null) {
            return ResponseDTO.userErrorParams("用户名已经注册了！");
        }

        if(!userAddChildForm.getPassword().equals(userAddChildForm.getConfirmPassword())) {
            return ResponseDTO.userErrorParams("密码不一致！");
        }

        UserEntity newUserEntity = SmartBeanUtil.copy(userAddChildForm, UserEntity.class);
        this.save(newUserEntity);

        UserVo userVo = SmartBeanUtil.copy(newUserEntity, UserVo.class);
        return ResponseDTO.ok(userVo);
    }

    public ResponseDTO<String> updateUser(UserUpdateForm userUpdateForm) {
        UserEntity userEntity = SmartBeanUtil.copy(userUpdateForm, UserEntity.class);
        this.updateById(userEntity);
        return ResponseDTO.ok();
    }

    //
    public UserEntity getUserByNameOrMobile(String userName, String mobile) {
        if (userName == null && mobile == null) {
            return null;
        }
        LambdaQueryWrapper<UserEntity> userEntityQueryWrapper = new LambdaQueryWrapper<>();
        if (userName == null) {
            userEntityQueryWrapper.eq(UserEntity::getMobile, mobile);
        } else if (mobile == null) {
            userEntityQueryWrapper.eq(UserEntity::getName, userName);
        } else {
            userEntityQueryWrapper.eq(UserEntity::getName, userName).or().eq(UserEntity::getMobile, mobile);
        }
        return this.getOne(userEntityQueryWrapper);
    }
    public ResponseDTO<List<UserVo>> getUserChildren(Long parentId) {

        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEntity::getParentId, parentId);
        List<UserEntity> list = this.list(lambdaQueryWrapper);
        List<UserVo> userVos = SmartBeanUtil.copyList(list, UserVo.class);
        return ResponseDTO.ok(userVos);
    }
}
