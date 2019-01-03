package com.hyjf.cs.message.handler;

/**
 * @author xiasq
 * @version JPushExceptionEnum, v0.1 2019/1/2 13:49
 */
public enum JPushExceptionEnum {
    SYSTEM_INNER_ERROR(1000, "极光系统内部错误"),
    NOT_SUPPORT_GET_ERROR(1001, "不支持 Get方法"),
    INCOMPLETE_PARAM_ERROR(1002, "缺少了必须的参数"),
    INVALID_PARAM_ERROR(1003, "参数值不合法"),
    AUTHORIZATION_ERROR(1004, "验证失败"),
    BODY_TOO_LONG_ERROR(1005, "消息体太大"),
    INVALID_APP_KEY_ERROR(1008, "app_key参数非法"),
    USER_NOT_EXISTS_ERROR(1011, "没有满足条件的推送目标，用户不存在"),
    ONLY_SUPPORT_HTTPS_ERROR(1020, "只支持 HTTPS请求"),
    SOCKET_TIMEOUT_ERROR(1030, "内部服务超时"),
    UNKNOWN_ERROR(9999, "调用极光发生未知错误")
    ;

    private int errorCode;
    private String errorMessage;

    JPushExceptionEnum(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static String getErrorMessageByCode(int errorCode){
        JPushExceptionEnum[] list = JPushExceptionEnum.values();
        for(JPushExceptionEnum e: list){
            if(errorCode == e.getErrorCode()){
                return e.getErrorMessage();
            }
        }
        return UNKNOWN_ERROR.getErrorMessage();
    }


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
