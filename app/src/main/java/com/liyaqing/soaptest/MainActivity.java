package com.liyaqing.soaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Vector;

import okhttp3.Call;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private String TAG="main";
    private CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Vector v= new Vector<Param>();
        v.add(new Param("userzjhm","152723194911108123"));
        v.add(new Param("yydsyqk","1"));
        Subscription subscription = RxSoap.request(v, "getAppointmentInfo")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e );
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: "+s);
                    }
                });
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(subscription);
//        Callback callback = new Callback() {
//            @Override
//            void succssful(String result) {
//
//            }
//
//            @Override
//            void error(Call call, Exception e) {
//
//            }
//        };
//
//        SoapTask soapTask = new SoapTask(callback);
//        HashMap<Integer, Object> map = new HashMap<>();
//
//
//
//
//        soapTask.execute(map);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSubscriptions.clear();
    }
}
