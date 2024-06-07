package com.example.final_project_app;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    ListView listView;//dùng để map với ListView trong main layout
    SanPhamAdapter adapterSanPham; //nguồn dữ liệu cho listview
    ArrayList<SanPham> dsSanPham;//quản lý danh sach san phẩm
    final String datafile = "data.txt";//file chứa dữ liệu
    String action;//gồm các thao tác NEW,EDIT,DELETE
    AssetManager assetManager;//đối tượng quản lý assert reouce
    SanPham p;//sản phẩm được chọn khi người dùng tap/click vào listview
    int index=-1;//vị trí của p
    //Tạo đối tượng gọi và xử lý kết quả trả về của một intent
    ActivityResultLauncher<Intent> startIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK) {
                        //login
                        //....
                        //product
                        if(result.getData().getStringExtra(SanPhamActivity.CLASSNAME).compareTo("SanPhamActivity")==0){
                            String tenSP;
                            int giaSP;
                            String hinhSP;
                            tenSP = result.getData().getStringExtra(SanPhamActivity.TENSP);
                            giaSP = Integer.parseInt(result.getData().getStringExtra(SanPhamActivity.GIASP));
                            hinhSP = result.getData().getStringExtra(SanPhamActivity.ANHSP);
                            p = new SanPham(hinhSP,tenSP,giaSP);
                            if(action.compareTo("NEW")==0){
                                newProduct(p);
                            }
                            if(action.compareTo("EDIT")==0){
                                editProduct(p);
                            }
                            if(action.compareTo("DELETE")==0){
                                deleteProduct();
                            }
                        }
                    }
                }
            }
    );
    private void newProduct(SanPham p){
        dsSanPham.add(p);
        adapterSanPham.add(p);
        writeToFile();
        adapterSanPham.notifyDataSetChanged();
    }           //phương thức tạo mới sản phẩm
    private void editProduct(SanPham p){
        try {
            dsSanPham.set(index,p);
            adapterSanPham.clear();
            adapterSanPham.addAll(dsSanPham);
            adapterSanPham.notifyDataSetChanged();
            writeToFile();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }          //phương thức cập nhật (edit) sản phẩm
    private void deleteProduct(){
        try {
            dsSanPham.remove(index);
            adapterSanPham.clear();
            adapterSanPham.addAll(dsSanPham);
            adapterSanPham.notifyDataSetChanged();
            writeToFile();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }                 //phương thức xóa sản phẩm
    private SanPhamAdapter readFromFile(){
        FileInputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader buffreader = null ;
        String spInfo[];
        SanPham p;
        SanPhamAdapter adapter = new SanPhamAdapter(this,R.layout.sanpham_layout);//khoi tao adaper
        dsSanPham = new ArrayList<SanPham>();
        try {
            fIn = openFileInput ( datafile );
            isr = new InputStreamReader ( fIn ) ;
            buffreader = new BufferedReader ( isr ) ;
            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                spInfo = readString.split("\'") ;
                p = new SanPham(spInfo[0],spInfo[1],Integer.parseInt(spInfo[2]));
                adapter.add(p);
                dsSanPham.add(p);
                readString = buffreader.readLine () ;
            }
        } catch (FileNotFoundException e) {
            return adapter;
        }catch (IOException e){
            return adapter;
        }
        return adapter;
    }        //Phương thức đọc dữ liệu từ datafile
    private void writeToFile(){
        OutputStreamWriter out;
        try {
            //ghi vao file data
            out = new OutputStreamWriter(openFileOutput( datafile, Context.MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(int i =0;i<dsSanPham.size();i++){
            try {
                out.write(dsSanPham.get(i).toString());
                out.write('\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }                   //Phương thức ghi dữ liệu vào datafile
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assetManager = getAssets();
        listView = (ListView) findViewById(R.id.lvSanSham);//mapping
        adapterSanPham = readFromFile();
        listView.setAdapter(adapterSanPham);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                p = (SanPham) adapterView.getItemAtPosition(i);
                index = i;
            }
        });
    } //Khởi tạo các đối tượng, mapping views
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }                   //Khởi tạo menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MainActivity.this,SanPhamActivity.class);
        if(item.getItemId()==R.id.menu_logout) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
        if(item.getItemId()==R.id.menu_newProduct) {
            action="NEW";

            startIntent.launch(intent);
        }
        if(item.getItemId()==R.id.menu_EditProduct) {
            action="EDIT";
            intent.putExtra(SanPhamActivity.ANHSP,p.getImName());
            intent.putExtra(SanPhamActivity.TENSP,p.getSpName());
            intent.putExtra(SanPhamActivity.GIASP,Integer.toString(p.getPrice()));
            startIntent.launch(intent);
        }
        if(item.getItemId()==R.id.menu_DeleteProduct) {
            //xu ly delete
            deleteProduct();
        }
        return super.onOptionsItemSelected(item);
    }    //Xử lý menu
}