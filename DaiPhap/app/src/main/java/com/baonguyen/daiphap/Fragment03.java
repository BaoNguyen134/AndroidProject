package com.baonguyen.daiphap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fragment03 extends Fragment {

    View myView;
    WebView webView03;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment03, container, false);
        webView03 = (WebView) myView.findViewById(R.id.web03);
        webView03.loadUrl("file:///android_asset/www/chuong3.html");
        return myView;
    }

}
