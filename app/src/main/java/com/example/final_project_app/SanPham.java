package com.example.final_project_app;

import androidx.annotation.NonNull;

public class SanPham {
    private String imName;
    private String spName;
    private int price;
    
    public SanPham(String imName,String spName,int price){
        this.imName=imName;
        this.spName=spName;
        this.price=price;
    }
    public String getImName(){return imName;}
    public void setImName(String imName){this.imName=imName;}
    public String getSpName(){return spName;}
    public int getPrice(){return price;}
    public void setPrice(int price){this.price=price;}
    @Override
    public String toString() {
        return imName+ '\'' + spName + '\'' + price ;
    }
}
