package com.liyaqing.soaptest;

/**
 * Created by yangjinxi on 2016/4/20.
 */
public class Param {
    public String key;
    public String value;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Param() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
