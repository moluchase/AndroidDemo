package com.example.administrator.mushroom.bean;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/17.
 */

public class DataBean {
    private int mId;//唯一标识符
    private String mHref;//文章地址
    private String mDate;//文章发表日期
    private String mType;//文章类型
    private String mTitle;//文章标题
    private String mIntroduction;//文章简介

    public DataBean(int id, String href, String date, String type, String title, String introduction) {
        mId = id;
        mHref = href;
        mDate = date;
        mType = type;
        mTitle = title;
        mIntroduction = introduction;
    }

    public DataBean(){}

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getHref() {
        return mHref;
    }

    public void setHref(String ulr) {
        mHref = ulr;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIntroduction() {
        return mIntroduction;
    }

    public void setIntroduction(String introduction) {
        mIntroduction = introduction;
    }
}
