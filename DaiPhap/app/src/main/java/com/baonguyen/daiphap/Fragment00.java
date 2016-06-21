package com.baonguyen.daiphap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fragment00 extends Fragment {
    View myView;
    WebView webView00;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment00, container, false);
        webView00 = (WebView) myView.findViewById(R.id.web00);
        webView00.loadUrl("file:///android_asset/www/chuong0.html");
        return myView;
    }
}
