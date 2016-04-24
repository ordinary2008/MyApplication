package com.plt.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/20.
 */
public class TweetList {

    private List<Tweet> tweetlist = new ArrayList<Tweet>();

    public TweetList() {
    }

    public List<Tweet> getTweetlist() {
        return tweetlist;
    }

    public void setTweetlist(List<Tweet> tweetlist) {
        this.tweetlist = tweetlist;
    }
}
