package com.liyaqing.soaptest;

import android.os.AsyncTask;
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

/**
 * Created by liyaqing on 2017/2/9.
 */

public class SoapTask extends AsyncTask<Map<Integer, Object>, Void, String> {
    private static final String NAMESPACE ="http://residentInterface.wondersgroup.com/";
    private static String transUrl="http://222.74.140.54:80/ordos-interface/ws/ordosAppointmentSEI?wsdl";//不公开
    //    private static final String METHOD_NAME ="getAppointmentInfo";
    private static String SOAP_ACTION ="";
    final static int envolopeVersion = SoapEnvelope.VER11;
    private Callback mCallback;

    public SoapTask(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Map<Integer, Object>... params) {
        String result = null;
        Map<Integer, Object> map = params[0];
        Vector<Param> v = (Vector<Param>) (map.get(0));
        String methodName = (String) map.get(1);
        SoapObject request =new SoapObject(NAMESPACE, methodName);
        for (int i = 0; i < v.size(); i++) {
            request.addProperty(v.get(i).getKey(),v.get(i).getValue());
        }
        System.out.println("request:"+ request);

        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;
        try {
            HttpTransportSE se = new HttpTransportSE(transUrl);
            //                    se.call(soapAction, envelope);    //ver11，第一个参数不能为空
            se.call("", envelope);//envolopeVersion为ver12第一个参数可以为空，必须接口支持ver12才行
            SoapObject response = (SoapObject) envelope.bodyIn;
            //response的处理需要根据返回的具体情况，基本都要进行下面一步
            Log.i("myresponse:",response.getProperty(0).toString());
            result = response.getProperty(0).toString();
//                    Gson gson = new Gson();
//                    MyObject myObject=gson.fromJson(responseString, MyObject.class);
//                    Log.i("MyObjest",myObject.getStatusFlag());
        } catch (IOException e) {
            e.printStackTrace();
            Respone respone = new Respone();
            respone.setStatusFlag("error");
            respone.setStatusMessage("io error");
            result = new Gson().toJson(respone);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Respone respone = new Respone();
            respone.setStatusFlag("error");
            respone.setStatusMessage("xml error");
            result = new Gson().toJson(respone);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String t) {
        mCallback.succssful(t);
    }
}
