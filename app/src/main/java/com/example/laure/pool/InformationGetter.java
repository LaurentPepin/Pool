package com.example.laure.pool;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by laure on 2016-12-29.
 */
public class InformationGetter extends AsyncTask<Void,Void,Void> {

    String TAG = "InformationGetter";
    String text;
    Context context;
    TextView textView;

    public InformationGetter(Context ctx, TextView textV){
        context = ctx;
        textView = textV;
    }


    @Override
    protected Void doInBackground(Void... voids) {


        try {
            Document document = Jsoup.connect("https://www.marqueur.com/poolpepin2015").get();



            Element parent = document.select(".t12b_n").first().parent().parent().parent();
            text = document.select(parent.cssSelector() + " .t12b_n").text();


        }
        catch (Exception e){e.printStackTrace();}

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        textView.setText(text);
    }
}
