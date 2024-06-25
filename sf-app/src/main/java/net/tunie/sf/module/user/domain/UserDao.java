package net.tunie.sf.module.user.domain;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao extends BaseMapper<UserEntity> {

    UserEntity selectByUserNameOrMobile(@Param("userName") String userName, @Param("mobile") String mobile);

    void bindParent(@Param("userId") Long userId, @Param("parentId") Long parentId);
}
