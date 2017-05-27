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
public class listAdapter extends BaseAdapter{
    Context mContext;
    String keyWord;
    ArrayList<Product> filter;
    ArrayList<Product> index;

    listAdapter(Context context,String keyWord){

        mContext=context;
        index=ApplicationClass.products;
        this.keyWord="(?i:.*"+keyWord+".*)";
        filter=new ArrayList<>();Log.i("Get it","txt");
        for(int i=0;i<index.size();i++){
            if(index.get(i).getName().matches(this.keyWord)) {
                filter.add(index.get(i));
            }
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        Log.i("Get it","items");
        if (listViewItem == null) {

            LayoutInflater vi = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            listViewItem = vi.inflate(R.layout.prod_item, null);
        }

        final ArrayList<Product> item=filter;

        TextView tv0 = (TextView) listViewItem.findViewById(R.id.mallname);
        TextView tv = (TextView) listViewItem.findViewById(R.id.name);
        TextView tv1 = (TextView) listViewItem.findViewById(R.id.price);
        TextView tv2 = (TextView) listViewItem.findViewById(R.id.stock);
        tv0.setText("쇼핑몰: "+item.get(position).mallName);
        tv.setText("상품명: "+item.get(position).getName());
        tv1.setText("가격: "+item.get(position).getPrice());
        tv2.setText("재고: "+item.get(position).getStock());

        return listViewItem;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        if(filter==null)
            return index.get(position);
        else
            return filter.get(position);
    }

    @Override
    public int getCount(){
        if(filter==null)
            return index.size();
        else
            return filter.size();
    }
    public ArrayList<Product> getIndex(){
        return index;
    }


}
