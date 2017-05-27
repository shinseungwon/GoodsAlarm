package com.example.ssw90.goodsalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    static ListView v2;
    listAdapter L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        v2=(ListView)findViewById(R.id.listx);

        SearchView searchview = (SearchView) findViewById(R.id.searchview);
        searchview.setFocusable(true);
        searchview.setIconified(false);
        searchview.requestFocusFromTouch();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.i("moved?",s);
                L = new listAdapter(ListActivity.this, s);
                v2.setAdapter(L);
                return false;
            }
        });

        v2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product target=(Product)L.getItem(position);
                Intent i=new Intent(ListActivity.this,ProductActivity.class);
                i.putExtra("mn",target.mallName);
                i.putExtra("na",target.getName());
                i.putExtra("pr",target.getPrice()+"");
                i.putExtra("st",target.getStock()+"");
                startActivity(i);
            }
        });
    }
}
