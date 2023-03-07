package com.example.chatroom.fcm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class SendPushNotification {

    Context context;
    String KEY = "AAAAC2QDq2w:APA91bGr8nwqdkQlPPnyLs3ZYWTOccePvKeRURQrEz-NPfbOnpUPM4MJEuncWlGgfxFRImEYqk7IV1ZGCHkRQEEPF9srq9H-FGX09sXXvD7E829DGpkQ4zPySC7z8CqpkKJkgDJP7Hi0";

    public SendPushNotification(Context context) {
        this.context = context;
    }
    public void startPush(String username , String messageBody , String token){
        HashMap<String , String > message = new HashMap<>();
        message.put("message",messageBody);
        message.put("title",username);
        sendPushToSingleInstance(message,token);
    }

    private void sendPushToSingleInstance(HashMap<String, String> message, String token) {
        String FCM_URL = "https://fcm.googleapis.com/fcm/send";
        StringRequest request = new StringRequest(Request.Method.POST, FCM_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                FirebaseMessaging.getInstance().subscribeToTopic("global_chat");
                Toast.makeText(context,"Message Send", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG","onErrorResponse: "+error.getMessage());
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String , Object> raw = new Hashtable<>();
                raw.put("data",new JSONObject(message));
                raw.put("to","/topics/"+token);
                return new JSONObject(raw).toString().getBytes();
            }

            public String getBodyContentType(){
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String , String > header = new HashMap<>();
                header.put("Authorization","key="+KEY);
                header.put("Content-Type","application/json");
                return header;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }
}
