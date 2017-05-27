package com.example.ssw90.goodsalarm;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by seungwon on 2016-03-22.
 */
//add,delete,edit
public class SQLiteAdapter extends SQLiteOpenHelper{//song 객체들을 db에 관리함,불러오거나 저장
    Context mContext;
    SQLiteDatabase dbase;
    public SQLiteAdapter(Context context){
        super(context, "GADB", null, 1);
        dbase=this.getWritableDatabase();
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        dbase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + "list" + " (mallname text, " + "name text, "+"price int, " +"stock int," +"target int," +"alarmed int" +");";
        dbase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void add(Product p){
        String sql="INSERT INTO list VALUES" +
                " ( \""+p.mallName+"\",\"" +
                p.getName()+"\",\""+
                p.getPrice()+"\",\"" +
                p.getStock()+"\",\"" +
                p.targetprice+"\",\"" +
                0+"\");";
        dbase.execSQL(sql);

    }

    public void delete(Product p){
        String sql="DELETE FROM list WHERE" +
                " mallname=\""+p.mallName+"\" AND name=\""+p.getName() +"\";";
        dbase.execSQL(sql);
    }

    public void edit(Product p,int price,int stock){
        String sql="UPDATE list SET price='"+price+"', stock=' "+stock+"' WHERE mallname=\""+p.mallName+"\"AND name=\""+p.getName() +"\";";
        dbase.execSQL(sql);
    }

    public boolean checkalarm(Product p){
        String sqlx="SELECT * FROM list WHERE" +
                " mallname=\""+p.mallName+"\" AND name=\""+p.getName() +"\";";
        Cursor c = dbase.rawQuery(sqlx, null);
        c.moveToFirst();
        int alarmed=c.getColumnIndex("alarmed");

        if(c.getInt(alarmed)==0)
            return true;
        else
            return false;
    }

    public void markalarm(Product p){
        String sql="UPDATE list SET alarmed='"+1+"' WHERE mallname=\""+p.mallName+"\"AND name=\""+p.getName() +"\";";
        dbase.execSQL(sql);
    }

    public ArrayList<Product> print(){
        ArrayList<Product> result=new ArrayList<>();

        Cursor c = dbase.rawQuery("SELECT * FROM list ORDER BY name COLLATE NOCASE ASC;", null);

        if(c.getCount()==0)
            return result;

        c.moveToFirst();

        int mallname=c.getColumnIndex("mallname");
        int name=c.getColumnIndex("name");
        int price=c.getColumnIndex("price");
        int stock=c.getColumnIndex("stock");
        int target=c.getColumnIndex("target");

            do {
                Product p = new Product(c.getString(mallname), c.getString(name), c.getInt(price), c.getInt(stock));
                p.targetprice=c.getInt(target);
                result.add(p);
            } while (c.moveToNext());

        return result;
    }
}
