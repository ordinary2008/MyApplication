package com.plt.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 查看源码按钮
     */
    private Button button;
    /**
     * 网址
     */
    private EditText url;
    /**
     * 内容
     */
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        getSupportActionBar().setTitle("OkHttpActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppManager.getAppManager().addActivity(this);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        url = (EditText) findViewById(R.id.url);
        content = (EditText) findViewById(R.id.content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //异步加载网页源码
                performAsyncHttpRequest();
                break;
            default:
                break;

        }
    }

    /**
     * 异步加载网页源码
     */
    private void performAsyncHttpRequest() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url.getText().toString())
                .build();
        Call call = client.newCall(request);
        // 1
        call.enqueue(new Callback() {
            // 2
            @Override
            public void onFailure(Call call, IOException e) {
                final String errorMMessage = e.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        content.setText(errorMMessage);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                if(response.isSuccessful()){
                    try {
                        final String responseStr = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                content.setText(responseStr);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
