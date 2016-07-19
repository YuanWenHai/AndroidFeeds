package com.will.androidfeeds.bean;

/**
 * Created by Will on 2016/7/19.
 * public account
 */
public class PAItem {
    private String title,time,link;
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return time;
    }
    public void setLink(String link){
        this.link = link;
    }
    public String getLink(){
        return link;
    }
}
