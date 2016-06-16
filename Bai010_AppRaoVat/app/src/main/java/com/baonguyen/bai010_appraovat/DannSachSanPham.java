package com.baonguyen.bai010_appraovat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DannSachSanPham extends AppCompatActivity {

    ListView lvSanPham;
    ArrayList<SanPham> mangSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dann_sach_san_pham);

        lvSanPham = (ListView) findViewById(R.id.listViewSanPham);
        mangSanPham = new ArrayList<SanPham>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadSanPham().execute("http://192.168.0.102/laravel/public/SanPham");
            }
        });

        lvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DannSachSanPham.this, CapNhatSanPham.class);
                intent.putExtra("data",mangSanPham.get(position).IdSP);
                startActivity(intent);
            }
        });
    }

    private class LoadSanPham extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return GET_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    mangSanPham.add(new SanPham(
                            object.getString("TenSP"),
                            object.getInt("Gia"),
                            object.getString("HinhSP"),
                            object.getInt("id")
                            ));
                }
                SanPhamAdapter adapter = new SanPhamAdapter(
                        DannSachSanPham.this,
                        R.layout.dong_san_pham,
                        mangSanPham
                );
                lvSanPham.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String GET_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
