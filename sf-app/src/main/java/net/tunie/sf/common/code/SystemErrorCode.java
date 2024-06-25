package net.tunie.sf.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemErrorCode implements ErrorCode {

    SYSTEM_ERROR(10001, "系统异常"),
    ;

    private final int code;

    private final String level;

    private final String msg;

    SystemErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.level = ErrorCode.LEVEL_SYSTEM;
    }
}
