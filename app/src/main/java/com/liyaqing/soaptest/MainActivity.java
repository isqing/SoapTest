package com.liyaqing.soaptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.yymc)
    TextView yymc;
    private String TAG = "main";
    private CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Vector v = new Vector<Param>();
        v.add(new Param("userzjhm", "152723194911108123"));
        v.add(new Param("yydsyqk", "1"));
        Subscription subscription = RxSoap.request(v, "getAppointmentInfo")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                        try {

                            JSONObject response=new JSONObject(s);
                            String flag=response.getString("statusFlag");
                            if (flag.equalsIgnoreCase("success")) {
                                String dataJson=response.getJSONArray("appointments").toString();
                                Log.i(TAG, "dataJson: "+dataJson);
                                List<Appointments.AppointmentsBean> appointments=new Gson().fromJson(dataJson,  new TypeToken<List<Appointments.AppointmentsBean>>() {
                                }.getType());
                                yymc.setText(appointments.get(0).getYymc());
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                });
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(subscription);
    }
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSubscriptions.clear();
    }
}
