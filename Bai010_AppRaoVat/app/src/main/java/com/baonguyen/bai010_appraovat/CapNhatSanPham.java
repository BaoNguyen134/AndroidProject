package com.baonguyen.bai010_appraovat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CapNhatSanPham extends AppCompatActivity {

    ImageView imageViewHinhCT;
    EditText editTextTenCT,editTextGiaCT;
    Button buttonSua, buttonXoa;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_san_pham);

        Intent intent = getIntent();
        id = intent.getIntExtra("data",0);
        //Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_LONG).show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new DocSanPham().execute("http://192.168.0.102/laravel/public/SanPham/"+id);
            }
        });

        AnhXa();

        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new CapNhatSP().execute("http://192.168.0.102/laravel/public/SanPham/"+id);
                    }
                });
            }
        });

        buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaSanPham().execute("http://192.168.0.102/laravel/public/SanPham/"+id);
                    }
                });
            }
        });
    }

    private class XoaSanPham extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return POST_URL_DELETE(params[0],"DELETE");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"Đã Xóa Sản Phẩm Thành Công",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CapNhatSanPham.this, DannSachSanPham.class);
            startActivity(intent);
        }
    }

    private String POST_URL_DELETE(String url, String type) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(1);

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
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }

    private class CapNhatSP extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            return POST_URL(params[0], "PUT");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"Cập Nhật Thành Công",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CapNhatSanPham.this, DannSachSanPham.class);
            startActivity(intent);
        }
    }

    private String POST_URL(String url, String type) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(4);
        nameValuePair.add(new BasicNameValuePair("TenSP", editTextTenCT.getText().toString()));

        String sHinh =  ImageView_To_String(imageViewHinhCT);
        nameValuePair.add(new BasicNameValuePair("HinhSP", sHinh));

        nameValuePair.add(new BasicNameValuePair("Gia", editTextGiaCT.getText().toString()));

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
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }

    public String ImageView_To_String(ImageView iv){
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String strHinh = Base64.encodeToString(byteArray, 0);
        return strHinh;
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
                editTextTenCT.setText(object.getString("TenSP"));
                editTextGiaCT.setText(String.valueOf(object.getInt("Gia")));
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
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void AnhXa(){
        imageViewHinhCT = (ImageView) findViewById(R.id.imageViewHinhCT);
        editTextTenCT = (EditText) findViewById(R.id.editTextTenCT);
        editTextGiaCT = (EditText) findViewById(R.id.editTextGiaCT);
        buttonSua = (Button) findViewById(R.id.buttonSua);
        buttonXoa = (Button) findViewById(R.id.buttonXoa);
    }

    public void String_To_ImageView(String strBase64, ImageView iv){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
    }
}
