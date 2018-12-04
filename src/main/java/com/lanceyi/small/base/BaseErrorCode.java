package com.lanceyi.small.base;

import java.util.HashMap;
import java.util.Map;

public class BaseErrorCode {
    public static final Integer successCode = 0;
    public static final Integer sysErrorCode = -1;
    public static final Integer notLoginCode = -2;
    public static final Integer paramErrorCode = -3;
    public static final Integer jsonFormatCode = -4;
    public static final Integer invokeErrorCode = -5;
    public static final Integer otherLogin = 40002;
    public static final Integer operationFailed = 1;
    public static final Map<Integer, String> systemErrorMap = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 498351352855245912L;

        {
            this.put(BaseErrorCode.successCode, "操作成功");
            this.put(BaseErrorCode.sysErrorCode, "系统错误");
            this.put(BaseErrorCode.notLoginCode, "未登录");
            this.put(BaseErrorCode.paramErrorCode, "参数错误");
            this.put(BaseErrorCode.jsonFormatCode, "json格式错误");
            this.put(BaseErrorCode.invokeErrorCode, "接口使用错误");
            this.put(BaseErrorCode.otherLogin, "在其他地方登录");
            this.put(BaseErrorCode.operationFailed, "操作失败");
        }
    };

    public BaseErrorCode() {
    }
}