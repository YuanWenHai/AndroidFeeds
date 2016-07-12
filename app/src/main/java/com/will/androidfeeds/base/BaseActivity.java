package com.will.androidfeeds.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Will on 2016/7/6.
 */
public class BaseActivity extends AppCompatActivity {
    protected Toast mToast;
    public void showToast(String message){
        if(mToast == null){
            mToast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
        }
        mToast.setText(message);
        mToast.show();
    }
}
