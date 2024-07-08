package net.tunie.sf.module.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.login.domain.RequestUser;
import net.tunie.sf.module.login.service.LoginService;
import net.tunie.sf.module.user.domain.dao.UserDao;
import net.tunie.sf.module.user.domain.entity.UserEntity;
import net.tunie.sf.module.user.domain.form.UserAddForm;
import net.tunie.sf.module.user.domain.form.UserUpdateForm;
import net.tunie.sf.module.user.domain.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserDao, UserEntity> {

    //@Resource
    //private UserDao userDao;
    //
    //
    //public ResponseDTO<String> addUser(UserAddForm userAddForm) {
    //    userDao.insert(SmartBeanUtil.copy(userAddForm, UserEntity.class));
    //    return ResponseDTO.ok();
    //}
    //
    //
    //public ResponseDTO<List<UserVo>> queryUser() {
    //    List<UserEntity> userEntities = userDao.selectList(null);
    //    List<UserVo> userVos = SmartBeanUtil.copyList(userEntities, UserVo.class);
    //    return ResponseDTO.ok(userVos);
    //}
    //
    public ResponseDTO<String> updateUser(UserUpdateForm userUpdateForm) {
        UserEntity userEntity = SmartBeanUtil.copy(userUpdateForm, UserEntity.class);
        this.updateById(userEntity);
        return ResponseDTO.ok();
    }

    //
    public UserEntity getUserByNameOrMobile(String userName, String mobile) {
        LambdaQueryWrapper<UserEntity> userEntityQueryWrapper = new LambdaQueryWrapper<>();
        userEntityQueryWrapper.eq(UserEntity::getName, userName).or().eq(UserEntity::getMobile, mobile);
        return this.getOne(userEntityQueryWrapper);
    }

    //
    //public UserEntity getUserById(Long userId) {
    //    return userDao.selectById(userId);
    //}
    //
    //public ResponseDTO<String> bindParent(Long userId, Long parentId) {
    //    UserEntity userEntity = userDao.selectById(parentId);
    //    if (userEntity == null) {
    //        return ResponseDTO.userErrorParams("绑定的帐户不存在");
    //    }
    //    userDao.bindParent(userId, parentId);
    //    return ResponseDTO.ok();
    //}
    //
    public ResponseDTO<List<UserVo>> getUserChildren(Long parentId) {

        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEntity::getParentId, parentId);
        List<UserEntity> list = this.list(lambdaQueryWrapper);
        List<UserVo> userVos = SmartBeanUtil.copyList(list, UserVo.class);
        return ResponseDTO.ok(userVos);
    }
}
