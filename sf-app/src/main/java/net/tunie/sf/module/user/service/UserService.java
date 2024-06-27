package net.tunie.sf.module.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import net.tunie.sf.common.code.UserErrorCode;
import net.tunie.sf.common.domain.ResponseDTO;
import net.tunie.sf.common.utils.SmartBeanUtil;
import net.tunie.sf.module.user.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public ResponseDTO<String> addUser(UserAddForm userAddForm) {
        userDao.insert(SmartBeanUtil.copy(userAddForm, UserEntity.class));
        return ResponseDTO.ok();
    }

    public ResponseDTO<UserVo> queryDetail(Long id) {
        UserEntity userEntity = userDao.selectById(id);
        UserVo giftVo = SmartBeanUtil.copy(userEntity, UserVo.class);
        return ResponseDTO.ok(giftVo);
    }

    public ResponseDTO<List<UserVo>> queryUser() {
        List<UserEntity> userEntities = userDao.selectList(null);
        List<UserVo> userVos = SmartBeanUtil.copyList(userEntities, UserVo.class);
        return ResponseDTO.ok(userVos);
    }

    public ResponseDTO<String> updateUser(UserUpdateForm userUpdateForm) {
        UserEntity userEntity = SmartBeanUtil.copy(userUpdateForm, UserEntity.class);
        userDao.updateById(userEntity);
        return ResponseDTO.ok();
    }

    public UserEntity getUserByNameOrMobile(String userName, String mobile) {
        return userDao.selectByUserNameOrMobile(userName, mobile);
    }

    public UserEntity getUserById(String userId) {
        return userDao.selectById(userId);
    }

    public ResponseDTO<String> bindParent(Long userId, Long parentId) {
        UserEntity userEntity = userDao.selectById(parentId);
        if (userEntity == null) {
            return ResponseDTO.userErrorParams("绑定的帐户不存在");
        }
        userDao.bindParent(userId, parentId);
        return ResponseDTO.ok();
    }

    public ResponseDTO<List<UserVo>> queryChildren(Long parentId) {
        QueryWrapper<UserEntity> queryWrapper = Wrappers.query();
        queryWrapper.eq("parent_id", parentId);
        List<UserEntity> userEntities = userDao.selectList(queryWrapper);
        List<UserVo> userVos = SmartBeanUtil.copyList(userEntities, UserVo.class);
        return ResponseDTO.ok(userVos);
    }
}
