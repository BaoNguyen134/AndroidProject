package com.baonguyen.projectelectronic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ChiTietSanPham extends AppCompatActivity {

    private TextView textViewTenCT,textViewGiaCT,textViewThongTinCT;
    private ImageView imageViewHinhCT;
    private Button buttonXoa;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        AnhXa();

        Intent intent = getIntent();
        id = intent.getIntExtra("data",0);
        //Toast.makeText(getApplicationContext(),""+id, Toast.LENGTH_LONG).show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new DocSanPham().execute("http://tuhoc360.net/laravel/public/SanPham/"+id);
            }
        });

        buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XoaSanPham().execute("http://tuhoc360.net/laravel/public/SanPham/"+id);
            }
        });
    }

    public void btnThem(View view) {
        Intent intent = new Intent(ChiTietSanPham.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class XoaSanPham extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return POST_URL_DELETE(params[0],"DELETE");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"Đã Xóa Sản Phẩm Thành Công", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ChiTietSanPham.this, DannSachSanPham.class);
            startActivity(intent);
            finish();
        }
    }

    private String POST_URL_DELETE(String url, String type) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List<BasicNameValuePair> nameValuePair = new ArrayList<BasicNameValuePair>(1);

        nameValuePair.add(new BasicNameValuePair("_method", type));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }

    private void AnhXa() {
        textViewTenCT = (TextView) findViewById(R.id.textViewTenCT);
        textViewGiaCT = (TextView) findViewById(R.id.textViewGiaCT);
        textViewThongTinCT = (TextView) findViewById(R.id.textViewThôngTinCT);
        imageViewHinhCT = (ImageView) findViewById(R.id.imageViewHinhCT);
        buttonXoa = (Button) findViewById(R.id.buttonXoa);
    }

    private class DocSanPham extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return GET_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            try {
                JSONObject object = new JSONObject(s);
                textViewTenCT.setText(object.getString("TenSP"));
                textViewGiaCT.setText(String.valueOf(object.getInt("Gia")+" đồng"));
                textViewThongTinCT.setText(object.getString("ChiTiet"));
                String_To_ImageView(object.getString("HinhSP"),imageViewHinhCT);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String GET_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try
        {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void String_To_ImageView(String strBase64, ImageView iv){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
    }
}
