package com.liyaqing.soaptest;

import okhttp3.Call;

/**
 * Created by liyaqing on 2017/2/9.
 */

public abstract class Callback{
    abstract void succssful(String result);
    abstract void error(Call call, Exception e);
}
