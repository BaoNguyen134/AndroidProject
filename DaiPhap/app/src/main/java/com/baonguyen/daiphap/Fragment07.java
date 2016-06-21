package com.baonguyen.daiphap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fragment07 extends Fragment {

    View myView;
    WebView webView07;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment07, container, false);
        webView07 = (WebView) myView.findViewById(R.id.web07);
        webView07.loadUrl("file:///android_asset/www/chuong7.html");
        return myView;
    }

}
