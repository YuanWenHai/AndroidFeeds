package com.will.androidfeeds.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Will on 2016/7/7.
 */
public class Tools {
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
}
