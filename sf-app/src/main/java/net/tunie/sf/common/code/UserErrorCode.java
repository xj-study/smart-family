package net.tunie.sf.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    PARAMS_ERROR(30001, "参数错误"),
    DATA_NOT_EXIST(30002, "数据不存在"),
    NO_PERMISSION(30005, "对不起，您没有权限访问此内容哦~"),
    LOGIN_STATE_INVALID(30007, "您还未登录或登录失效，请重新登录！"),
    ;

    private final int code;

    private final String level;

    private final String msg;

    UserErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.level = ErrorCode.LEVEL_USER;
    }
}
