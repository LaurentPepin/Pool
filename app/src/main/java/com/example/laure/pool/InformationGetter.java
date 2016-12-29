package com.example.laure.pool;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Gravity;
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

    String TAG = "InformationGetter";
    Context context;
    TableLayout tableLayout;


    String[] poolersNames;
    int nPoolers;
    int[] yesterdayStats;
    int[] totalStats;



    public InformationGetter(Context ctx, TableLayout tb){
        context = ctx;
        tableLayout = tb;
    }


    @Override
    protected Void doInBackground(Void... voids) {


        try {
            Document document = Jsoup.connect("https://www.marqueur.com/poolpepin2015").get();
            Element mainTable = document.select(".t12b_n").first().parent().parent().parent();

            //Get poolers names
            String text = document.select(mainTable.cssSelector() + " .t12b_n").text();
            String[] separatedNames = text.split(" ");
            poolersNames = getFullNames(separatedNames);

            //Get stats yesterday
            String stats = document.select(mainTable.cssSelector() + " .t12nc").text();
            String[] separatedStats = stats.split(" ");
            getStats(separatedStats);

        }
        catch (Exception e){e.printStackTrace();}

        return null;
    }

    public void getStats(String[] strings){
        String[] wantedStats = new String[strings.length/2];
        for(int i=0; i<strings.length/2; i++){
            wantedStats[i] = strings[i*2+1];
        }
        int[] yStats = new int[nPoolers];
        for(int i=0; i<nPoolers; i++){
            yStats[i] = Integer.parseInt(wantedStats[i*2]);
        }
        int[] tStats = new int[nPoolers];
        for(int i=0; i<nPoolers; i++){
            tStats[i] = Integer.parseInt(wantedStats[i*2+1]);
        }
        yesterdayStats = yStats;
        totalStats = tStats;
    }

    public String[] getFullNames (String[] strings){
        nPoolers = strings.length/2;
        String[] names = new String[nPoolers];
        for(int i=0; i<nPoolers; i++){
            names[i] = strings[i*2] + " " + strings[i*2+1].substring(0,1) + ".";
        }
        return names;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        for(int i=0; i<nPoolers; i++){

            TableRow tableRow = new TableRow(context);

            tableRow.setGravity(Gravity.CENTER);

            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            params.weight = 1;

            tableRow.setLayoutParams(params);

            TextView textViewName = new TextView(context);
            textViewName.setText(i+1 + " " + poolersNames[i]);
            textViewName.setLayoutParams(params);

            TextView textViewYesterdayStats = new TextView(context);
            textViewYesterdayStats.setText(""+yesterdayStats[i]);
            textViewYesterdayStats.setGravity(Gravity.CENTER);
            textViewYesterdayStats.setLayoutParams(params);

            TextView textViewTotalStats = new TextView(context);
            textViewTotalStats.setText(""+totalStats[i]);
            textViewTotalStats.setGravity(Gravity.CENTER);
            textViewTotalStats.setLayoutParams(params);

            tableRow.addView(textViewName);
            tableRow.addView(textViewYesterdayStats);
            tableRow.addView(textViewTotalStats);

            tableLayout.addView(tableRow);

        }



    }
}
