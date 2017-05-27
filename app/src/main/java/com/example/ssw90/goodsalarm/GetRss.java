package com.example.ssw90.goodsalarm;

/**
 * Created by seungwon on 2016-05-04.
 */
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.os.AsyncTask;
import android.util.Log;

public class GetRss extends AsyncTask<Void, Void, Void> {
    ArrayList<Product> products=new ArrayList<>();
    String MallName="";
    String uri = "";
    URL url;
    String tagname;
    String title="";
    String price="";
    String stock="";
    Boolean flag = false;

    GetRss(String Mallname,String uriName){
        this.MallName=Mallname;
        this.uri=uriName;
    }
    @Override
    protected Void doInBackground(Void... params) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            url = new URL(uri);
            InputStream in = url.openStream();
            xpp.setInput(in, "utf-8");
            int eventType = xpp.getEventType();
            while(true){
                eventType = xpp.getEventType();
                if(eventType == XmlPullParser.START_TAG&&xpp.getName().equals("item"))
                    break;
                else
                    xpp.next();
            }
            while(eventType != XmlPullParser.END_DOCUMENT ) {
                if(eventType == XmlPullParser.START_TAG) {
                    tagname = xpp.getName();
                } else if(eventType == XmlPullParser.TEXT) {
                    if(tagname.equals("name")&&title.equals("")) {
                        title += xpp.getText();
                    }
                    else if(tagname.equals("price")) {
                        price += xpp.getText();

                    }
                    else if(tagname.equals("stock")) {
                        stock += xpp.getText();
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    tagname = xpp.getName();

                    if(tagname.equals("item")) {
                        price=price.trim();
                        stock=stock.trim();

                        products.add(new Product(MallName,title,Integer.parseInt(price),Integer.parseInt(stock)));
                        //Log.i("RssInfo",title+"/"+price+"/"+stock+"/!/");
                        title="";
                        price="";
                        stock="";
                    }
                }
                eventType = xpp.next();
            }
            flag = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
