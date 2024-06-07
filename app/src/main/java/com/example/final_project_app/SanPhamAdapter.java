package com.example.final_project_app;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    Activity context;// chứa thông tin activity
    int resource; //chưa thông tin resource
    AssetManager assetManager;//đối tượng quản lý assets
    InputStream is; //đối tượng đọc ảnh
    Bitmap bitmap; //đối tượng chứa ảnnh đã đọc được
    String imageFolder;//thư mục chứa ảnh

    public SanPhamAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context; //khởi tạo đối tượng context
        this.resource = resource;//khởi tạo đối tượng resource
        this.assetManager = context.getAssets();//khởi tạo assets manager
        this.imageFolder ="Images";//thư mục chứa các hình ảnh
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =this.context.getLayoutInflater();
        View customerView =inflater.inflate(this.resource,null);
        ImageView imageView = customerView.findViewById(R.id.img);//mapping image view
        TextView tvName = customerView.findViewById(R.id.tenSP);//mapping name
        TextView tvGia = customerView.findViewById(R.id.giaSP);//mapping gia

        SanPham sp = getItem(position);//get SanPham

        try {
            //mở file ảnh
            is = this.assetManager.open(imageFolder+"/"+sp.getImName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //tạo file ảnh
        bitmap = BitmapFactory.decodeStream(is);
        imageView.setImageBitmap(bitmap);//gán ảnh cho image view
        tvName.setText(sp.getSpName());//gán tên sản phẩm cho textview name
        tvGia.setText(String.format("%d",sp.getPrice())+" VND");//format giá sp và gán cho textview
        return customerView;
    }
}
