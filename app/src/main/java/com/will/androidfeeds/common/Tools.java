package com.will.androidfeeds.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.will.androidfeeds.base.MyApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Will on 2016/7/7.
 */
public class Tools {
    private static Toast mToast;
    public static String string2Md5(String str){
        String md5;
        try{
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(str.getBytes());
            md5 = bytesToHexString(mDigest.digest());
        }catch (NoSuchAlgorithmException n){
            md5 = String.valueOf(str.hashCode());
        }
        return md5;
    }
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    public static boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getGlobalContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }
    public static boolean isWiFiAvailable(){
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getGlobalContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo != null && wifiInfo.isConnected();
    }
    public static void showToast(String message){
        if(mToast == null){
            mToast = Toast.makeText(MyApplication.getGlobalContext(),"",Toast.LENGTH_SHORT);
        }
        mToast.setText(message);
        mToast.show();
    }
}
