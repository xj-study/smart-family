package net.tunie.sf.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnexpectedErrorCode implements ErrorCode {

    BUSINESS_ERROR(20001, "业务繁忙，请稍后重试"),

    ;

    private final int code;

    private final String level;

    private final String msg;

    UnexpectedErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.level = LEVEL_UNEXPECTED;
    }
}
