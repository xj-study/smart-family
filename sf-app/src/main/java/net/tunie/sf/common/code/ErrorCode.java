package net.tunie.sf.common.code;

public interface ErrorCode {

    /**
     * 系统异常
     */
    String LEVEL_SYSTEM = "system";
    /**
     * 用户异常
     */
    String LEVEL_USER = "user";

    /**
     * 未知异常
     */
    String LEVEL_UNEXPECTED = "unexpected";

    int getCode();

    String getLevel();

    String getMsg();
}
