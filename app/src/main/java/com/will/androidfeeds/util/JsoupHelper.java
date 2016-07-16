package com.will.androidfeeds.util;

import com.will.androidfeeds.bean.CsdnItem;
import com.will.androidfeeds.bean.HKItem;

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
            content =  doc.select("div.article_content").html();
        }
        return content;
    }
    public static String getCsdnMobileContentFromSource(String source){
        Document doc = Jsoup.parse(source);
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
        Elements content = doc.select("div.blog_article_c.clearfix");
        return content.toString();
    }
    public static List<CsdnItem> getCsdnListItemFromSource(String source){
        //Log.e("source.length = :",""+source.length());
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
    public static List<HKItem> getHKItemFromSource(String source){
        Document doc = Jsoup.parse(source);
        ArrayList<HKItem> list = new ArrayList<>();
        HKItem item;
        Elements articles = doc.select("div.blog-index");
        articles = articles.select("article");
        for( Element article :articles){
            item = new HKItem();
            item.setTitle(article.select("h1.entry-title").text());
            item.setLink(article.select("h1.entry-title").select("a[href]").attr("href"));
            item.setTime(article.select("time[datetime]").text());
            item.setPreview(article.select("div.entry-content").html());
            list.add(item);
        }
        return list;
    }
    public static boolean hasMoreHKItem(String source){
        Document doc = Jsoup.parse(source);
        return !doc.select("div.pagination").select("a.prev").isEmpty();
    }
}
