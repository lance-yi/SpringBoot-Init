package com.lanceyi.small.base;

/**
 * @author Lance YI
 * @date 2018/12/4 10:20
 */
public class SuccessResponseTemplate<T> extends ApiResponseTemplate<T> {
    public SuccessResponseTemplate(T data) {
        Integer successCode = BaseErrorCode.successCode;
        this.setCode(successCode);
        this.setMessage((String)BaseErrorCode.systemErrorMap.get(successCode));
        this.setData(data);
    }

    public SuccessResponseTemplate() {
        Integer successCode = BaseErrorCode.successCode;
        this.setCode(successCode);
        this.setMessage((String)BaseErrorCode.systemErrorMap.get(successCode));
    }

    public SuccessResponseTemplate(String msg) {
        this.setCode(BaseErrorCode.successCode);
        this.setMessage(msg);
    }

    public static <T> SuccessResponseTemplate ok(T data) {
        return new SuccessResponseTemplate(data);
    }

    public static SuccessResponseTemplate ok(String msg) {
        return new SuccessResponseTemplate(msg);
    }

    public static SuccessResponseTemplate ok() {
        return new SuccessResponseTemplate();
    }

}
