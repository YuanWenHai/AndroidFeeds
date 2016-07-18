package com.will.androidfeeds.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.ksoichiro.android.observablescrollview.ObservableWebView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The MarkedView is the Markdown viewer.
 *
 * Created by mittsu on 2016/04/25.
 */
public final class MWebView extends ObservableWebView {

    private static final String TAG = MWebView.class.getSimpleName();
    private static final String IMAGE_PATTERN = "!\\[(.*)\\]\\((.*)\\)";

    private String previewText;
    public MWebView(Context context) {
        this(context, null);
    }

    public MWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void show(){

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                loadUrl(previewText);
            } else {
                evaluateJavascript(previewText, null);
            }
        }

    @TargetApi(11)
    @SuppressLint("SetJavaScriptEnabled")
    private void init(){
        // default browser is not called.
        setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    loadUrl(previewText);
                } else {
                    evaluateJavascript(previewText, null);
                }
            }
        });
        loadUrl("file:///android_asset/html/md_preview.html");
        getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /** load Markdown text from file path. **/
    public void loadMDFilePath(String filePath){
        loadMDFile(new File(filePath));
    }

    /** load Markdown text from file. **/
    public void loadMDFile(File file){
        String mdText = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readText = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((readText = bufferedReader.readLine()) != null) {
                stringBuilder.append(readText);
                stringBuilder.append("\n");
            }
            fileInputStream.close();
            mdText = stringBuilder.toString();

        } catch(FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException:" + e);
        } catch(IOException e) {
            Log.e(TAG, "IOException:" + e);
        }
        setMDText(mdText);
    }

    /** set show the Markdown text. **/
    public void setMDText(String text){
        text2Mark(text);
        show();
    }

    private void text2Mark(String mdText){

        String bs64MdText = imgToBase64(mdText);
        String escMdText = escapeForText(bs64MdText);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            previewText = String.format("javascript:preview('%s')", escMdText);

        } else {
            previewText = String.format("preview('%s')", escMdText);
        }
    }

    private String escapeForText(String mdText){
        String escText = mdText.replace("\n", "\\\\n");
        escText = escText.replace("\r","");
        escText = escText.replace("'", "\\\'");
        return escText;
    }

    private String imgToBase64(String mdText){
        Pattern ptn = Pattern.compile(IMAGE_PATTERN);
        Matcher matcher = ptn.matcher(mdText);
        if(!matcher.find()){
            return mdText;
        }

        String imgPath = matcher.group(2);
        if(isUrlPrefix(imgPath) || !isPathExChack(imgPath)) {
            return mdText;
        }
        String baseType = imgEx2BaseType(imgPath);
        if(baseType.equals("")){
            // image load error.
            return mdText;
        }

        File file = new File(imgPath);
        byte[] bytes = new byte[(int) file.length()];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException:" + e);
        } catch (IOException e) {
            Log.e(TAG, "IOException:" + e);
        }
        String base64Img = baseType + Base64.encodeToString(bytes, Base64.NO_WRAP);

        return mdText.replace(imgPath, base64Img);
    }

    private boolean isUrlPrefix(String text){
        return text.startsWith("http://") || text.startsWith("https://");
    }

    private boolean isPathExChack(String text){
        return text.endsWith(".png")
                || text.endsWith(".jpg")
                || text.endsWith(".jpeg")
                || text.endsWith(".gif");
    }

    private String imgEx2BaseType(String text){
        if(text.endsWith(".png")){
            return "data:image/png;base64,";
        }else if(text.endsWith(".jpg") || text.endsWith(".jpeg")){
            return "data:image/jpg;base64,";
        }else if(text.endsWith(".gif")){
            return "data:image/gif;base64,";
        }else{
            return "";
        }
    }

}
