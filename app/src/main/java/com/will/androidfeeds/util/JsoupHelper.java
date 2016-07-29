package com.will.androidfeeds.util;

import com.will.androidfeeds.bean.CsdnItem;
import com.will.androidfeeds.bean.DanLewItem;
import com.will.androidfeeds.bean.DroidYueItem;
import com.will.androidfeeds.bean.HKItem;
import com.will.androidfeeds.bean.PAItem;
import com.will.androidfeeds.bean.StylingAndroidItem;

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
    public static int getCsdnListItemCount(String source){
        Document doc = Jsoup.parse(source);
        String temp = doc.select("div#papelist").text();
        return Integer.parseInt(temp.substring(0,temp.indexOf("条")));
    }
    public static List<HKItem> getHKItemFromSource(String source){
        Document doc = Jsoup.parse(source);
        ArrayList<HKItem> list = new ArrayList<>();
        HKItem item;
        Elements articles = doc.select("div.blog-index");
        articles = articles.select("article");
        for( Element article :articles){
            item = new HKItem();
            article.select("img").remove();
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
    public static String getHKContentFromSource(String source){
        Document doc = Jsoup.parse(source);
        Elements content = doc.select("div.entry-content");
        Elements imgs = content.select("img");
        for (Element img :imgs){
            String url = img.attr("src");
            img.attr("src","http:hukai.me"+url);
        }
        return content.html();
    }
    public static List<PAItem> getPAItemFromSource(String source){
        List<PAItem> list = new ArrayList<>();
        PAItem item;
        Document doc = Jsoup.parse(source);
        Elements items = doc.select("div.pagedlist_item");
        for(Element element :items){
            item = new PAItem();
            item.setLink(element.select("a.question_link").attr("href"));
            item.setTime(element.select("span.timestamp").text());
            item.setTitle(element.select("a.question_link").text());
            list.add(item);
        }
        return list;
    }
    public static String getPAContentFromSource(String source){
        Document doc = Jsoup.parse(source);
        return doc.select("div.rich_media_area_primary").html();
    }

    /**
     * 从网页中提取技术小黑屋条目，需要提到的是，该网页默认返回9条item，所以如果item数不足9，可认为已到末页。
     * @param source 网页源代码
     * @return 条目list
     */
    public static List<DroidYueItem> getDroidYueItemFromSource(String source){
        String host = "http://droidyue.com";
        List<DroidYueItem> list = new ArrayList<>();
        DroidYueItem item;
        Document doc = Jsoup.parse(source);
        Elements articles = doc.select("div.blog-index").select("article");
        for(Element article :articles){
            item = new DroidYueItem();
            article.select("img").remove();
            item.setTitle(article.select("h1.entry-title").text());
            item.setLink(host + article.select("h1.entry-title").select("a[href]").attr("href"));
            item.setTime(article.select("time[datetime]").text());
            item.setPreview(article.select("div.entry-content").html());
            list.add(item);
        }
        return list;
    }
    public static String getDroidYueContentFromSource(String source){
        Document doc = Jsoup.parse(source);
        Elements content = doc.select("div.entry-content");
        Elements imgs = content.select("img");
        /*for (Element img :imgs){
            String url = img.attr("src");
            img.attr("src","http://droidyue.com"+url);
        }*/
        return content.html();
    }

    /**
     * 默认每页返回十条.
     * @param source
     * @return
     */
    public static List<StylingAndroidItem> getStylingAndroidItemFromSource(String source){
        List<StylingAndroidItem> list = new ArrayList<>();
        StylingAndroidItem item;
        Document doc  = Jsoup.parse(source);
        Elements articles = doc.select("div#primary").select("article");
        for(Element element :articles){
            item = new StylingAndroidItem();
            item.setTitle(element.select("h1.entry-title").text());
            item.setLink(element.select("h1.entry-title").select("a").attr("href"));
            Elements preview = element.select("div.entry-content").select("p");
            preview.select("a").remove();
            item.setPreview(preview.html());
            item.setTime(element.select("time.entry-date").text());
            list.add(item);
        }
        return list;
    }
    public static String getStylingAndroidContentFromSource(String source){
        Document doc = Jsoup.parse(source);
        Elements article = doc.select("div.entry-content");
        article.select("div").remove();
        article.select("p[prefix]").remove();
        return article.html();
    }

    /**
     * 默认返回五条
     * @param source source
     * @return result
     */
    public static List<DanLewItem> getDanLewItemFromSource(String source){
        String host = "http://blog.danlew.net";
        ArrayList<DanLewItem> list = new ArrayList<>();
        DanLewItem item;
        Document doc = Jsoup.parse(source);
        Elements articles = doc.select("main#content").select("article");
        for(Element element : articles){
            item = new DanLewItem();
            item.setTitle(element.select("h2.post-title").text());
            item.setLink(host+element.select("h2.post-title").select("a").attr("href"));
            item.setPreview(element.select("section.post-excerpt").text());
            item.setTime(element.select("time.post-date").text());
            list.add(item);
        }
        return list;
    }
    public static String getDanLewContentFromSource(String source){
        Document doc = Jsoup.parse(source);
        String header = doc.select("header.post-header").html();
        String content = doc.select("section.post-content").html();
        return header+content;
    }
}
