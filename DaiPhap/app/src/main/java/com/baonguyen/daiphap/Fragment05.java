package com.baonguyen.daiphap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fragment05 extends Fragment {

    View myView;
    WebView webView05;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment05, container, false);
        webView05 = (WebView) myView.findViewById(R.id.web05);
        webView05.loadUrl("file:///android_asset/www/chuong5.html");
        return myView;
    }

}
