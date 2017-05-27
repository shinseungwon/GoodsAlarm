package com.example.ssw90.goodsalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
/*
가격

        재고

        디비에 있는거 하나씩 뽑아

        뽑은거랑 같은걸 rss에서 가져온거에서 찾아(쇼핑몰,이름 같은거)

        여기서 재고가 0에서 양수가 되면 알람

        여기서 가격이 목표이 이상이었다가 내리면 알람

        끝
*/
public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ApplicationClass.products= getLists();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    //Log.i("Test Msg", "None");
                    SQLiteAdapter S=new SQLiteAdapter(MyService.this);
                    ArrayList<Product> CompareList;

                    CompareList=S.print();//내가 저장해둔거
                    ApplicationClass.products= getLists();//rss에서 따온거

                    for(int i=0;i<CompareList.size();i++){Log.i("MyService","loop");
                        for(int j=0;j<ApplicationClass.products.size();j++){
                            if((CompareList.get(i).getName().equals(ApplicationClass.products.get(j).getName()))&&
                                    (CompareList.get(i).mallName.equals(ApplicationClass.products.get(j).mallName))){Log.i("MyService","founditem");
                                //먼저 업데이트 하고

                                int pricetemp=0;
                                Log.i("MyService",CompareList.get(i).getPrice()+"/"+CompareList.get(i).targetprice+"/!/"+ ApplicationClass.products.get(j).getPrice());

                                if(CompareList.get(i).getPrice()!=ApplicationClass.products.get(j).getPrice())
                                    CompareList.get(i).cPrice(ApplicationClass.products.get(j).getPrice());

                                if(CompareList.get(i).getStock()!=ApplicationClass.products.get(j).getStock())
                                    CompareList.get(i).cStock(ApplicationClass.products.get(j).getStock());

                                S.edit(CompareList.get(i),ApplicationClass.products.get(j).getPrice(),ApplicationClass.products.get(j).getStock());


                                    //재고 생김
                                    if (CompareList.get(i).getStock() != 0 &&CompareList.get(i).targetprice==0) {
                                        if(S.checkalarm(CompareList.get(i))) {
                                            Notification(CompareList.get(i), 0);
                                            S.markalarm(CompareList.get(i));
                                        }
                                    }
                                    //목표가격 맞춤
                                    if (CompareList.get(i).getPrice() <= CompareList.get(i).targetprice) {
                                        if(S.checkalarm(CompareList.get(i))) {
                                            Notification(CompareList.get(i), 1);
                                            S.markalarm(CompareList.get(i));
                                        }
                                    }


                            }
                        }
                    }
                    Log.i("MyService","Running");

                    try{Thread.sleep(3000);}catch(Exception e){}
                }
            }
        };
        Thread t=new Thread(r);
        t.start();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private ArrayList<Product> getLists(){

        GetRss mall_1=new GetRss("한성쇼핑몰","http://silver6427.dothome.co.kr/test/RSS01.xml");
        GetRss mall_2=new GetRss("정시쇼핑몰","http://silver6427.dothome.co.kr/test/RSS02.xml");
        GetRss mall_3=new GetRss("IT 마켓","http://silver6427.dothome.co.kr/test/RSS03.xml");
        mall_1.execute(null, null, null);
        mall_2.execute(null, null, null);
        mall_3.execute(null, null, null);

        while(true){
            if(mall_1.flag&&mall_2.flag&&mall_3.flag)
                break;
        }

        ArrayList<Product> result=new ArrayList<>();
        result.addAll(mall_1.products);
        result.addAll(mall_2.products);
        result.addAll(mall_3.products);
        return result;
    }

    public void Notification(Product p,int param){//param -> 0:재고생김 ,1:가격달성
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ico);
        mBuilder.setTicker("Notification.Builder");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setNumber(10);
        mBuilder.setContentTitle(p.mallName+": "+p.getName()+" !");
        if(param==0)
            mBuilder.setContentText("상품이 입고되었습니다. 서두르세요!");
        else
            mBuilder.setContentText("상품이 설정해둔 "+p.targetprice+"원 보다 싸진 "+p.getPrice()+"원에 팔리고 있습니다!");
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        mBuilder.setPriority(Notification.PRIORITY_MAX);

        nm.notify(111, mBuilder.build());
    }
}
