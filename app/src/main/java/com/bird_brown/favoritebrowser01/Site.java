package com.bird_brown.favoritebrowser01;

import java.io.Serializable;

public class Site implements Serializable {
    private String title; //タイトル用
    private String url; //URL用

    public Site() {}

    public Site(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
