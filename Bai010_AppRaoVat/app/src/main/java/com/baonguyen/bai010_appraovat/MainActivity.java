package com.baonguyen.bai010_appraovat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnThem, btnDanhSach;
    private ImageView imgHinh;
    private EditText edtTen, edtGia;
    int REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new LuuSanPham().execute();
                    }
                });

            }
        });

        btnDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DannSachSanPham.class);
                startActivity(intent);
            }
        });
    }

    private class LuuSanPham extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            return POST_URL("http://192.168.0.102/laravel/public/SanPham",null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                Toast.makeText(MainActivity.this,"Thêm Sản Phẩm Có ID: "+object.getInt("id"),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
        }
    }

    private String POST_URL(String url, String type) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(4);
        nameValuePair.add(new BasicNameValuePair("TenSP", edtTen.getText().toString()));

        String sHinh =  ImageView_To_String(imgHinh);
        nameValuePair.add(new BasicNameValuePair("HinhSP", sHinh));

        nameValuePair.add(new BasicNameValuePair("Gia", edtGia.getText().toString()));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AnhXa(){
        btnDanhSach = (Button) findViewById(R.id.buttonDanhSach);
        btnThem = (Button) findViewById(R.id.btnThem);
        imgHinh = (ImageView) findViewById(R.id.imageViewHinh);
        edtTen = (EditText) findViewById(R.id.editTextTen);
        edtGia = (EditText) findViewById(R.id.editTextGia);
    }
}
