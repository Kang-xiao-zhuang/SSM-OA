package com.zhuang.common.config.exception;

import com.zhuang.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * description: CustomException
 * date: 2023/3/1 20:45
 * author: Zhuang
 * version: 1.0
 */

/**
 * 自定义全局异常类
 */
@Data
public class CustomException extends RuntimeException {

    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param code
     * @param message
     */
    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public CustomException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}