package com.baonguyen.daiphap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fragment04 extends Fragment {

    View myView;
    WebView webView04;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment04, container, false);
        webView04 = (WebView) myView.findViewById(R.id.web04);
        webView04.loadUrl("file:///android_asset/www/chuong4.html");
        return myView;
    }

}
