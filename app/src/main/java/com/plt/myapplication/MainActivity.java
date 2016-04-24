package com.plt.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DoubleClickExitHelper mDoubleClickExit;

    private Button okhttpBtn;

    private Button frescoBtn;

    private Button tweetListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDoubleClickExit = new DoubleClickExitHelper(this);

        okhttpBtn = (Button) findViewById(R.id.okhttpBtn);
        okhttpBtn.setOnClickListener(this);

        frescoBtn = (Button) findViewById(R.id.frescoBtn);
        frescoBtn.setOnClickListener(this);

        tweetListBtn = (Button) findViewById(R.id.tweetListBtn);
        tweetListBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent ;
        switch (v.getId()) {
            case R.id.okhttpBtn:
                intent = new Intent(this, OkHttpActivity.class);
                startActivity(intent);
                break;
            case R.id.frescoBtn:
                intent = new Intent(this, FrescoActivity.class);
                startActivity(intent);
                break;
            case R.id.tweetListBtn:
                intent = new Intent(this, TweetListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 是否退出应用
//            return mDoubleClickExit.onKeyDown(keyCode, event);

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("提示");
            alertDialog.setMessage("按确定退出程序");
            alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
