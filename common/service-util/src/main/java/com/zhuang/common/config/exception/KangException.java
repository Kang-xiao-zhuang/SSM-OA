package com.zhuang.common.config.exception;

import com.zhuang.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class KangException extends RuntimeException {

    private Integer code;//状态码
    private String msg;//描述信息

    public KangException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public KangException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
