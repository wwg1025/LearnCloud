package com.wen.springcloud.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @param <T>
 * @author wwg
 * @descrip 通用响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应内容
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
