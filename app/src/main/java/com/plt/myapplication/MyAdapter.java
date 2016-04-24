package com.plt.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by lenovo on 2016/4/21.
 */
public class MyAdapter implements ListAdapter {

    private List<Tweet> tweets;
    private LayoutInflater mInflater;
    private Context context;

    public MyAdapter(Context context, List<Tweet> tweets) {
        this.mInflater = LayoutInflater.from(context);
        this.tweets = tweets;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return tweets.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = tweets.get(position);
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_tweet_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView1.setText(tweet.getAuthor());
        viewHolder.textView2.setText(tweet.getBody());
        Glide.with(context).load(tweet.getImgBig()).into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PicassoSampleActivity.class);
                intent.putExtra("image_url", tweet.getImgBig());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView imageView;

        public ViewHolder(View view) {
            textView1 = (TextView) view.findViewById(R.id.textView);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            imageView = (ImageView) view.findViewById(R.id.imageView2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
