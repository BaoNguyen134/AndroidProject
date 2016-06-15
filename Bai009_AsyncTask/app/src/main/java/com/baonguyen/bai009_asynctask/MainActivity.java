package com.baonguyen.bai009_asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView tvNoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.buttonStart);
        tvNoiDung = (TextView) findViewById(R.id.tvNoiDung);

        btnStart.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XuLy().execute();
                    }
                });
            }
        });
    }

    class XuLy extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvNoiDung.append("START\n");
        }

        @Override
        protected String doInBackground(String... params) {
            return "FINISH";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvNoiDung.append(s+"\n");
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
