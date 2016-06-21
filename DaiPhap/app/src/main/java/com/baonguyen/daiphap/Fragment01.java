package com.baonguyen.daiphap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fragment01 extends Fragment {

    View myView;
    WebView webView01;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment01, container, false);
        webView01 = (WebView) myView.findViewById(R.id.web01);
        webView01.loadUrl("file:///android_asset/www/chuong1.html");
        return myView;
    }

}
