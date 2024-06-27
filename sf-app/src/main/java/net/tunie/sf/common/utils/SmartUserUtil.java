package net.tunie.sf.common.utils;

import net.tunie.sf.constant.UserTypeConst;
import net.tunie.sf.module.user.domain.entity.UserEntity;

public class SmartUserUtil {

    public static int getUserType(UserEntity userEntity) {
        return getUserType(userEntity.getParentId());
    }

    public static int getUserType(long parentId) {
        if (-1 == parentId) {
            return UserTypeConst.PARENT;
        } else {
            return UserTypeConst.CHILD;
        }
    }

    public static boolean getUserChildFlag(int type) {
        return UserTypeConst.CHILD == type;
    }
}
