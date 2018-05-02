package com.freelancer.Utility;

public class ResultObject {
    private String errorMsg;
    private String successMsg;
    private Object data;

    public ResultObject(String errorMsg, String successMsg, Object data) {
        this.errorMsg = errorMsg;
        this.successMsg = successMsg;
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
