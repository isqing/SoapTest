package com.liyaqing.soaptest;

import android.util.Log;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by liyaqing on 2017/2/9.
 */

public class RxSoap {
    private static final String NAMESPACE = "http://residentInterface.wondersgroup.com/";
    private static String transUrl = "http://222.74.140.54:80/ordos-interface/ws/ordosAppointmentSEI?wsdl";//不公开
    //    private static final String METHOD_NAME ="getAppointmentInfo";
    private static String SOAP_ACTION = "";
    final static int envolopeVersion = SoapEnvelope.VER11;

    public static Observable<String> request(final Vector<Param> v, final String methodName) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SoapObject request = new SoapObject(NAMESPACE, methodName);
                for (int i = 0; i < v.size(); i++) {
                    request.addProperty(v.get(i).getKey(), v.get(i).getValue());
                }
                System.out.println("request:" + request);
                final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = true;
                try {
                    HttpTransportSE se = new HttpTransportSE(transUrl);
                    //                    se.call(soapAction, envelope);    //ver11，第一个参数不能为空
                    se.call("", envelope);//envolopeVersion为ver12第一个参数可以为空，必须接口支持ver12才行
                    SoapObject response = (SoapObject) envelope.bodyIn;
                    //response的处理需要根据返回的具体情况，基本都要进行下面一步
                    Log.i("myresponse:", response.getProperty(0).toString());
                    String responseString = response.getProperty(0).toString();
//                    Gson gson = new Gson();
//                    MyObject myObject=gson.fromJson(responseString, MyObject.class);
//                    Log.i("MyObjest",myObject.getStatusFlag());
                    subscriber.onNext(responseString);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
