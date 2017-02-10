package com.liyaqing.soaptest;

/**
 * Created by liyaqing on 2017/2/9.
 */

public class Respone<T> {

    private String statusFlag;
    private String statusMessage;

    private T data;

    public Respone() {
    }

    public String getStatusFlag() {

        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
