package com.lanceyi.small.base;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Lance YI
 * @date 2018/12/4 10:06
 */
public class ApiResponseTemplate<T> {

    @ApiModelProperty("错误码")
    private Integer code;

    @ApiModelProperty("描述")
    private String message;

    @ApiModelProperty("返回数据")
    private T data = null;

    public ApiResponseTemplate() {
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
