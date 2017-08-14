package com.zju.utils.dwz;

public class AjaxResponseVo {

    public static String STATUS_CODE_SUCCESS = "200";
    public static String STATUS_CODE_ERROR = "300";
    public static String STATUS_CODE_OVERTIME = "301";

    // callbackType类型
    public final static String CALLBACK_TYPE_CLOSE_CURRENT = "closeCurrent";
    public final static String CALLBACK_TYPE_FORWARD = "forward";
    private String statusCode;

    private String navTabId;

    private String rel;

    private String callbackType;

    private String forwardUrl;


    private String message;


    public AjaxResponseVo() {

    }

    public AjaxResponseVo(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public AjaxResponseVo(String statusCode, String message, String navTabId) {
        this.statusCode = statusCode;
        this.message = message;
        this.navTabId = navTabId;
    }

    public AjaxResponseVo(String statusCode, String message, String navTabId, String callbackType) {
        this.statusCode = statusCode;
        this.message = message;
        this.navTabId = navTabId;
        this.callbackType = callbackType;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getNavTabId() {
        return navTabId;
    }

    public void setNavTabId(String navTabId) {
        this.navTabId = navTabId;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }


    public String getMessage() {
        if (message == null || "".equals(message)) {
            if (STATUS_CODE_SUCCESS.equals(statusCode)) {
                return "操作成功";
            } else if (STATUS_CODE_ERROR.equals(statusCode)) {
                return "操作失败";
            } else if (STATUS_CODE_OVERTIME.equals(statusCode)) {
                return "操作超时";
            }
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCodeAndMessage(String statusCode, String message) {
        this.setStatusCode(statusCode);
        this.message = message;
    }


}
