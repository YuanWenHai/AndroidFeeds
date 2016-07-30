package com.will.androidfeeds;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.will.androidfeeds.base.BaseFragment;

/**
 * Created by Will on 2016/7/29.
 */
public class TestFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_test,container,false);
        View view = getInflaterWithTheme(inflater,R.style.StylingAndroidTheme).inflate(R.layout.fragment_test,container,false);
        TextView textView = (TextView) view.findViewById(R.id.test_html);
        textView.setText(Html.fromHtml(content));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
    private String content = "<br>Hi，我是<b>胡凯</b>，就职于腾讯从事Android开发的工作。有任何问题，你可以直接写<a href=\"mailto:hukai.me@gmail.com\"><b>邮件</b></a>给我，也可以通过<a href=\"http://weibo.com/kesenhoo\" target=\"_blank\"><b>微博</b></a>联系到我，另外还可以关注我的<a href=\"https://github.com/kesenhoo\" target=\"_blank\"><b>Github</b></a>，欢迎一起学习交流！</br>";
}
