package com.baonguyen.daiphap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fragment08 extends Fragment {

    View myView;
    WebView webView08;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment08, container, false);
        webView08 = (WebView) myView.findViewById(R.id.web08);
        webView08.loadUrl("file:///android_asset/www/chuong8.html");
        return myView;
    }

}
