package com.will.androidfeeds.bean;

import java.io.Serializable;

/**
 * Created by Will on 2016/7/20.
 * 技术小黑屋,Item
 */
public class DroidYueItem implements Serializable{
    private String title,link,time,preview;
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link = link;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getPreview(){
        return preview;
    }
    public void setPreview(String preview){
        this.preview = preview;
    }
}
