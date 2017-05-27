package com.example.ssw90.goodsalarm;

/**
 * Created by ssw90 on 2016-11-20.
 */

public class Product {

    public String mallName="";

    private String name="";
    private int price=0;
    private int stock=0;

    public int targetprice=0;

    Product(String mallName,String name,int price, int stock){
        this.mallName=mallName;
        this.name=name;
        this.price=price;
        this.stock=stock;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public int getStock(){
        return stock;
    }

    public void cPrice(int price){
        this.price=price;
    }

    public void cStock(int stock){
        this.price=stock;
    }
}
