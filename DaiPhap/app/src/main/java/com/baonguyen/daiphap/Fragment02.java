package com.baonguyen.daiphap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fragment02 extends Fragment {

    View myView;
    WebView webView02;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment02, container, false);
        webView02 = (WebView) myView.findViewById(R.id.web02);
        webView02.loadUrl("file:///android_asset/www/chuong2.html");
        return myView;
    }

}
