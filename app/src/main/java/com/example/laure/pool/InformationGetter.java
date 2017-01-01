package com.example.laure.pool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.w3c.dom.Text;

/**
 * Created by laure on 2016-12-29.
 */
public class InformationGetter extends AsyncTask<Void,Void,Void> {


    boolean isLive = false;
    String TAG = "InformationGetter";
    Context context;
    TableLayout tableLayout;
    TextView textView;
    String[] poolersNames;
    int nPoolers;
    int[] yesterdayStats;
    int[] totalStats;

    public InformationGetter(Context ctx,TableLayout tb, TextView tx){
        context = ctx;
        tableLayout = tb;
        textView = tx;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect("https://www.marqueur.com/poolpepin2015").get();

            String title = document.select(".titre").first().text();

            if(title.equals("EN DIRECT")){
                isLive = true;
                Log.e(TAG,"Is live");

            }

            if(isLive){
                //find number of poolers and get poolers names
                Element live = document.select(".titre").first().parent();
                Element tableLive = document.select(live.cssSelector() + " .t10gc").first().parent().parent();
                String text = document.select(tableLive.cssSelector() + " .t12b_n").text();
                String[] separatedNames = text.split(" ");
                poolersNames = getFullNames(separatedNames);

                Element mainTable = document.select(".titre").get(1).parent().parent().parent().parent();

                //Get stats
                String stats = document.select(mainTable.cssSelector() + " .t12nc").text();
                String[] separatedStats = stats.split(" ");
                getStats(separatedStats);

            }
            else {
                Element mainTable = document.select(".t12b_n").first().parent().parent().parent();

                //Get poolers names
                String text = document.select(mainTable.cssSelector() + " .t12b_n").text();
                String[] separatedNames = text.split(" ");
                poolersNames = getFullNames(separatedNames);

                //Get stats
                String stats = document.select(mainTable.cssSelector() + " .t12nc").text();
                String[] separatedStats = stats.split(" ");
                getStats(separatedStats);
            }

        }
        catch (Exception e){e.printStackTrace();}

        return null;
    }

    public void getStats(String[] strings){
        String[] wantedStats = new String[nPoolers*2];
        for(int i=0; i<nPoolers*2; i++){
            wantedStats[i] = strings[i*2+1];
        }
        int[] yStats = new int[nPoolers];
        for(int i=0; i<nPoolers; i++){
            if(!wantedStats[i*2].equals("-")) {
                yStats[i] = Integer.parseInt(wantedStats[i * 2]);
            }
            else{
                yStats[i] = 0;
            }
        }
        int[] tStats = new int[nPoolers];
        for(int i=0; i<nPoolers; i++){
            if(!wantedStats[i*2+1].equals("-")) {
                tStats[i] = Integer.parseInt(wantedStats[i * 2 + 1]);
            }
            else{
                tStats[i] = 0;
            }
        }
        yesterdayStats = yStats;
        totalStats = tStats;
    }

    public String[] getFullNames (String[] strings){
        nPoolers = strings.length/2;
        String[] names = new String[nPoolers];
        for(int i=0; i<nPoolers; i++){
            //names[i] = strings[i*2] + " " + strings[i*2+1].substring(0,1) + ".";
            names[i] = strings[i*2];
            int index = names[i].indexOf('-');
            if(index>0){
                names[i] = names[i].substring(0,index+2) + ".";
            }

        }
        return names;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(isLive){
            textView.setText("Direct");
        }

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        TableRow tableRowTitles = new TableRow(context);
        tableRowTitles.setLayoutParams(params);
        tableRowTitles.setGravity(Gravity.CENTER);



        TableLayout tableLayoutNew = new TableLayout(context);
        for(int i=0; i<nPoolers; i++){

            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setLayoutParams(params);
            tableRow.setBackgroundResource(R.drawable.border);
            tableRow.setPadding(10,10,10,10);

            for(int j=0; j<3; j++){
                TextView textView = new TextView(context);
                String text;
                if(j==0) {
                    text = " " + Integer.toString(i + 1) + " " + poolersNames[i];
                    //text = "Nom";
                }
                else if (j==1){
                    text = "" + yesterdayStats[i];
                    textView.setGravity(Gravity.CENTER);
                    //text = "y";
                }
                else {
                    text = ""+totalStats[i];
                    textView.setGravity(Gravity.CENTER);
                    //text = "Total";
                }
                textView.setText(text);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
                textView.setLayoutParams(layoutParams);
                tableRow.addView(textView);
            }
            tableLayoutNew.addView(tableRow);
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(tableLayoutNew);
        tableLayout.addView(scrollView);
    }
}
