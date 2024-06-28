package net.tunie.sf.common.utils;

import net.tunie.sf.module.login.domain.RequestUser;

public class SmartRequestUtil {

    private static final ThreadLocal<RequestUser> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    public static void setRequestUser(RequestUser user) {
        REQUEST_THREAD_LOCAL.set(user);
    }

    public static RequestUser getRequestUser() {
        return REQUEST_THREAD_LOCAL.get();
    }

    public static Long getRequestUserId() {
        RequestUser requestUser = getRequestUser();
        return requestUser == null ? null : requestUser.getUserId();
    }

    public static Long getUserParentId() {
        RequestUser requestUser = getRequestUser();
        if (requestUser == null) {
            return null;
        }
        Long userId = requestUser.getParentId();
        if (userId == -1) {
            userId = requestUser.getUserId();
        }
        return userId;
    }

    public static void remove() {
        REQUEST_THREAD_LOCAL.remove();
    }
}
