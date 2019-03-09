package com.example.sbbreusablecupchallenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.*;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void scanCode(View view) {
        try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            startActivity(marketIntent);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(contents)));
            }

        }
    }

    public void request() {
        Log.d("HELLO", "request() called");
        // Create okhttp3 request builder.
        Request.Builder builder = new Request.Builder();
        // Set url.
        builder = builder.url("https://bxmbrpyq1h.execute-api.eu-west-2.amazonaws.com/Dev/dbupdate");
        // Create request object.
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        char q = '"';
        String jsonBody = "{"+q+"user_id"+q+":"+q+"USER_123456"+q+"}";
        Log.d("HELLO",jsonBody);
        String header = "hello world";

        RequestBody body = RequestBody.create(JSON, jsonBody);

        Request request = new Request.Builder()
                .url("https://bxmbrpyq1h.execute-api.eu-west-2.amazonaws.com/Dev/dbupdate")
                .put(body) //PUT
                .addHeader("Authorization", header)
                .build();

        // Create a new Call object.
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);

        Log.d("HELLO", "about to enter the try");

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HELLO", "callback failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("HELLO", "callback response");
                Log.d("HELLO", "reponse body is: " + response.body().string());
            }
        });
    }

}

