package net.tunie.sf.common.domain;

import lombok.Data;
import net.tunie.sf.common.code.ErrorCode;
import net.tunie.sf.common.code.UserErrorCode;
import org.apache.commons.lang3.StringUtils;

@Data
public class ResponseDTO<T> {

    public static final int OK_CODE = 0;
    public static final String OK_MSG = "操作成功";

    /**
     * 返回 code
     */
    private Integer code;
    /**
     * 级别
     */
    private String level;
    /**
     * 描述信息
     */
    private String msg;

    private Boolean ok;

    private T data;


    public ResponseDTO(Integer code, String level, String msg, Boolean ok, T data) {
        this.code = code;
        this.level = level;
        this.msg = msg;
        this.ok = ok;
        this.data = data;
    }

    public ResponseDTO(Integer code, String level, String msg, Boolean ok) {
        this.code = code;
        this.level = level;
        this.msg = msg;
        this.ok = ok;
    }


    public ResponseDTO(ErrorCode errorCode, String msg, Boolean ok, T data) {
        this.code = errorCode.getCode();
        this.level = errorCode.getLevel();

        if (StringUtils.isNotBlank(msg)) {
            this.msg = msg;
        } else {
            this.msg = errorCode.getMsg();
        }
        this.ok = ok;
        this.data = data;
    }


    public static <T> ResponseDTO<T> ok() {
        return new ResponseDTO<>(OK_CODE, null, OK_MSG, true);
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return new ResponseDTO<>(OK_CODE, null, OK_MSG, true, data);
    }

    // --------------------------------------------错误码-------------------------------------------
    public static <T> ResponseDTO<T> userErrorParams() {
        return new ResponseDTO<>(UserErrorCode.PARAMS_ERROR, null, false, null);
    }

    public static <T> ResponseDTO<T> userErrorParams(String msg) {
        return new ResponseDTO<>(UserErrorCode.PARAMS_ERROR, msg, false, null);
    }

    public static <T> ResponseDTO<T> error(ErrorCode errorCode) {
        return new ResponseDTO<>(errorCode, null, false, null);
    }

    public static <T> ResponseDTO<T> error(ErrorCode errorCode, Boolean ok) {
        return new ResponseDTO<>(errorCode, null, ok, null);
    }

    public static <T> ResponseDTO<T> error(ErrorCode errorCode, String msg) {
        return new ResponseDTO<>(errorCode, msg, false, null);
    }

    public static <T> ResponseDTO<T> error(ErrorCode errorCode, T data) {
        return new ResponseDTO<>(errorCode, null, false, data);
    }
}