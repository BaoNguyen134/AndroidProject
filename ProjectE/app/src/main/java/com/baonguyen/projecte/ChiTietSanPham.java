package com.baonguyen.projecte;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ChiTietSanPham extends AppCompatActivity {

    private TextView textViewTenCT,textViewGiaCT,textViewThongTinCT;
    private ImageView imageViewHinhCT;
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
    }

    public void GoiDatHang(View view) {
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0946830620"));
        startActivity(call);
    }

    private void AnhXa() {
        textViewTenCT = (TextView) findViewById(R.id.textViewTenCT);
        textViewGiaCT = (TextView) findViewById(R.id.textViewGiaCT);
        textViewThongTinCT = (TextView) findViewById(R.id.textViewThôngTinCT);
        imageViewHinhCT = (ImageView) findViewById(R.id.imageViewHinhCT);
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
