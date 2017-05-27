package com.example.ssw90.goodsalarm;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by seungwon on 2016-03-22.
 */
public class mainAdapter extends BaseAdapter{
    Context mContext;

    ArrayList<Product> index;

    mainAdapter(Context context){

        mContext=context;
        SQLiteAdapter S=new SQLiteAdapter(mContext);
        index=S.print();

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        //Log.i("Get it","items");
        if (listViewItem == null) {

            LayoutInflater vi = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            listViewItem = vi.inflate(R.layout.prod_item, null);
        }

        final ArrayList<Product> item=index;

        TextView tv0 = (TextView) listViewItem.findViewById(R.id.mallname);
        TextView tv = (TextView) listViewItem.findViewById(R.id.name);
        TextView tv1 = (TextView) listViewItem.findViewById(R.id.price);
        TextView tv2 = (TextView) listViewItem.findViewById(R.id.stock);
        tv0.setText("쇼핑몰: "+item.get(position).mallName);
        tv.setText("상품명: "+item.get(position).getName());
        if(item.get(position).targetprice!=0)
            tv1.setText("가격: "+item.get(position).getPrice()+"(목표가격: "+item.get(position).targetprice+" 원)");
        else
            tv1.setText("가격: "+item.get(position).getPrice());
        tv2.setText("재고: "+item.get(position).getStock());

        return listViewItem;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
            return index.get(position);
    }

    @Override
    public int getCount(){
            return index.size();
    }
    public ArrayList<Product> getIndex(){
        return index;
    }


}
