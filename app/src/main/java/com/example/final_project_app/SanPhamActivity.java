package com.example.final_project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class SanPhamActivity extends AppCompatActivity {
    public static String TENSP = "TENSP";
    public static String GIASP = "GIASP";
    public static String ANHSP = "ANHSP";
    public static String CLASSNAME="SANPHAMACTIVITY";
    AssetManager assetManager;
    Spinner spinner ;
    TextView edtTenSp,edtGiaSp;
    Button button;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        spinner =(Spinner) findViewById(R.id.spImage);
        imageView = (ImageView) findViewById(R.id.imHinhSap);
        edtTenSp = (EditText) findViewById(R.id.edtTenSp);
        edtGiaSp = (EditText) findViewById(R.id.edtGiaSP);
        String[] fileNames;
        assetManager = getAssets();
        try {
            fileNames = assetManager.list("Images");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayAdapter<String> adapterFiles = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fileNames);
        spinner.setAdapter(adapterFiles);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView imName = (TextView) view;
                InputStream is;
                try {
                    is = assetManager.open("Images/"+imName.getText().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        String x = getIntent().getStringExtra(SanPhamActivity.GIASP);
        edtTenSp.setText(getIntent().getStringExtra(SanPhamActivity.TENSP));
        edtGiaSp.setText(x);
        int spinnerPosition = adapterFiles.getPosition(getIntent().getStringExtra(SanPhamActivity.ANHSP));
        spinner.setSelection(spinnerPosition);

    }

    public void onClickSave(View view) {
        Intent intent = getIntent();
        intent.putExtra(SanPhamActivity.CLASSNAME,SanPhamActivity.class.getSimpleName());
        intent.putExtra(SanPhamActivity.TENSP,edtTenSp.getText().toString());
        intent.putExtra(SanPhamActivity.GIASP,edtGiaSp.getText().toString());
        intent.putExtra(SanPhamActivity.ANHSP,(String)spinner.getSelectedItem());
        setResult(RESULT_OK,intent);
        finish();
    }
}