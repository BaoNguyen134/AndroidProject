package com.baonguyen.bai001_linearlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ClickLayout(View view) {
        setContentView(R.layout.linear01);
    }

    public void Click01(View view) {
        setContentView(R.layout.linear02);
    }

    public void Click02(View view) {
        setContentView(R.layout.linear03);
    }

    public void Click03(View view) {
        setContentView(R.layout.linear04);
    }

    public void Click05(View view) {
        setContentView(R.layout.linear05);
    }

    public void Click06(View view) {
        setContentView(R.layout.activity_main);
    }
}
