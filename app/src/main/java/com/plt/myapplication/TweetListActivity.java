package com.plt.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TweetListActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    private static final int REFRESH_STATUS =0;

    private Handler refreshHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_STATUS :
                    initTweetListData();
                    //停止刷新
                    swipeLayout.setRefreshing(false);
                    break;
            }
        }
    };

    /**
     * 给ListView添加下拉刷新
     */
    private SwipeRefreshLayout swipeLayout;

    /**
     * ListView
     */
    private ListView tweetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);

        getSupportActionBar().setTitle("TweetListActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppManager.getAppManager().addActivity(this);

        swipeLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(this);

        tweetList = (ListView) findViewById(R.id.tweet_list);

        initTweetListData();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        refreshHandler.sendEmptyMessageDelayed(REFRESH_STATUS, 1500);
        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //停止刷新
                swipeLayout.setRefreshing(false);
            }
        }, 5000);// 5秒后发送消息，停止刷新
        */
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

    /**
     * 异步加载动弹列表
     */
    private void initTweetListData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.oschina.net/action/openapi/tweet_list?access_token=b25f1836-bdd3-429a-b1c3-67d6325ec396&user=-1")
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
                        Toast.makeText(TweetListActivity.this, errorMMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                if(response.isSuccessful()){
                    try {
                        final String responseStr = response.body().string();
                        Gson gson = new Gson();
                        final TweetList tweetsList = gson.fromJson(responseStr, TweetList.class);
                        final List<Tweet> tweets = tweetsList.getTweetlist();

                        final int code = response.code();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(TweetListActivity.this, tweets.get(0).getBody(), Toast.LENGTH_LONG).show();
                                tweetList.setAdapter(new MyAdapter(TweetListActivity.this, tweets));
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    private void initView() {

    }





}
