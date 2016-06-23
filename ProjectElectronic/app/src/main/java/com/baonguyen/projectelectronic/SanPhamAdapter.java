package com.baonguyen.projectelectronic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SanPhamAdapter extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<SanPham> arraySanPham;

    public SanPhamAdapter(Context context, int layout, List<SanPham> sanPhamList) {
        myContext = context;
        myLayout = layout;
        arraySanPham = sanPhamList;
    }

    @Override
    public int getCount() {
        return arraySanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);
        // Ánh xạ, gán giá trị
        TextView tvTenSanPham = (TextView) convertView.findViewById(R.id.textViewTen);
        tvTenSanPham.setText(arraySanPham.get(position).TenSP);
        TextView tvGiaSanPham = (TextView) convertView.findViewById(R.id.textViewGia);
        tvGiaSanPham.setText(String.valueOf(arraySanPham.get(position).GiaSP));
        ImageView imgHinhSP = (ImageView) convertView.findViewById(R.id.imageViewHinhSP);
        String_To_ImageView(arraySanPham.get(position).HinhAnh, imgHinhSP);
        return convertView;
    }

    public void String_To_ImageView(String strBase64, ImageView iv){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
    }
}
