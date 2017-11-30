package me.laiyijie.job.admin.service.exception;

/**
 * Created by laiyijie on 11/28/17.
 */
public class BusinessException extends RuntimeException {
    private Integer code;
    private String msg;

    public BusinessException() {
        this(500, "undifined error");
    }

    public BusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(String msg) {
        this(400, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
