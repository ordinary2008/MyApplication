package com.plt.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class FrescoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button imgButton;

    private EditText imgUrl;

    private SimpleDraweeView draweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(getApplicationContext());

        setContentView(R.layout.activity_fresco);

        getSupportActionBar().setTitle("FrescoActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppManager.getAppManager().addActivity(this);

        imgButton = (Button) findViewById(R.id.imgButton);
        imgButton.setOnClickListener(this);
        imgUrl = (EditText) findViewById(R.id.imgUrl);
        draweeView = (SimpleDraweeView) findViewById(R.id.imageView);
        draweeView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgButton:
                //异步加载图片
                showImg();
                break;
            case R.id.imageView:
                //单独查看图片
                showImgSimple();
                break;
            default:
                break;
        }
    }

    /**
     * 单独查看图片
     */
    private void showImgSimple() {
        Intent intent = new Intent(this, PicassoSampleActivity.class);
        intent.putExtra("image_url", imgUrl.getText().toString());
        startActivity(intent);
    }

    /**
     * 加载图片
     */
    private void showImg() {
        draweeView.setImageURI(Uri.parse(imgUrl.getText().toString()));
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
