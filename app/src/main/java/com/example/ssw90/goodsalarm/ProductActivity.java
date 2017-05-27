package com.example.ssw90.goodsalarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        final String mallName=getIntent().getStringExtra("mn");
        final String name=getIntent().getStringExtra("na");
        final String price=getIntent().getStringExtra("pr");
        final String stock=getIntent().getStringExtra("st");

        Log.i("result",mallName+"/"+name+"/"+price+"/"+stock);

        TextView tv1=(TextView)findViewById(R.id.mallname1);
        TextView tv2=(TextView)findViewById(R.id.name1);
        TextView tv3=(TextView)findViewById(R.id.price1);
        TextView tv4=(TextView)findViewById(R.id.stock1);

        tv1.setText("쇼핑몰: "+mallName);
        tv2.setText("상품명: "+name);
        tv3.setText("가격: "+price);
        tv4.setText("재고: "+stock);

        Button b1=(Button)findViewById(R.id.cancel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button b2=(Button)findViewById(R.id.okay);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("okay","yes");
                SQLiteAdapter A=new SQLiteAdapter(ProductActivity.this);
                Product p=new Product(mallName,name,Integer.parseInt(price),Integer.parseInt(stock));
                EditText e=(EditText)findViewById(R.id.targetprice);
                int temp=0;

                if(!(e.getText().toString()==""))
                    temp=Integer.parseInt(e.getText().toString());

                Log.i("et",temp+"");
                    p.targetprice=temp;
                A.add(p);
                finish();
            }
        });
    }
}
