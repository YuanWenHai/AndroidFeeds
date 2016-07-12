package com.will.androidfeeds.util;

import com.will.androidfeeds.bean.CsdnItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 2016/7/6.
 */
public class JsoupHelper {
    public static String getCsdnContentFromSource(String source){
        Document doc = Jsoup.parse(source);
        String content = doc.select("div.markdown_views").toString();
        if(content.isEmpty()){
            content =  doc.select("div.article_content").toString();
        }
        return content;
    }
    public static List<CsdnItem> getCsdnListItemFromSource(String source){
        ArrayList<CsdnItem> list = new ArrayList<>();
        CsdnItem csdnItem;
        Document doc = Jsoup.parse(source);
        Elements articleList = doc.select("div#article_list");
        Elements items = articleList.get(0).children();
        for(Element element : items){
            csdnItem = new CsdnItem();
            csdnItem.setlink(element.select("span.link_title").select("a[href]").attr("href"));
            csdnItem.setTime(element.select("span.link_postdate").text());
            csdnItem.setTitle(element.select("span.link_title").text());
            list.add(csdnItem);
        }
        return list;
    }
    public static String getCsdnListItemCount(String source){
        Document doc = Jsoup.parse(source);
        String temp = doc.select("div#papelist").text();
        return temp.substring(0,temp.indexOf("条"));
    }
}
