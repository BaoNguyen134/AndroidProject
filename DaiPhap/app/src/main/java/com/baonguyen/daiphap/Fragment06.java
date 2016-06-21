package com.baonguyen.daiphap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Fragment06 extends Fragment {

    View myView;
    WebView webView06;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment06, container, false);
        webView06 = (WebView) myView.findViewById(R.id.web06);
        webView06.loadUrl("file:///android_asset/www/chuong6.html");
        return myView;
    }

}
