package com.example.laure.pool;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
            }

            if(isLive){
                //find number of poolers and get poolers names
                Element live = document.select(".titre").first().parent();
                Element tableLive = document.select(live.cssSelector() + " .t10gc").first().parent().parent();
                String text = document.select(tableLive.cssSelector() + " .t12b_n").text();
                String[] separatedNames = text.split(" ");
                nPoolers = separatedNames.length / 2;

                Element mainTable = document.select(".titre").get(1).parent().parent().parent().parent();

                //Get poolers names
                text = document.select(mainTable.cssSelector() + " .t12b_n").text();
                separatedNames = text.split(" ");
                poolersNames = getFullNames(separatedNames);


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
        if(!isLive) {
            nPoolers = strings.length / 2;
        }
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
            textView.setText(R.string.Direct);
        }

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);


        TableLayout tableLayoutNew = new TableLayout(context);
        tableLayoutNew.setPadding(0,0,0,0);
        for(int i=0; i<nPoolers; i++){

            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setLayoutParams(params);
            if(i%2==1){

                if(i==nPoolers-1){
                    tableRow.setBackgroundResource(R.drawable.lastrowodd);
                }
                else {
                    tableRow.setBackgroundResource(R.drawable.rowodd);
                }
            }
            else{
                if(i==nPoolers-1){
                    tableRow.setBackgroundResource(R.drawable.lastroweven);
                }
                else {
                    tableRow.setBackgroundResource(R.drawable.roweven);
                }
            }



            for(int j=0; j<7; j++){
                TextView textView = new TextView(context);

                String text;
                if(j==0) {
                    text = " " + Integer.toString(i + 1) + " " + poolersNames[i];
                }
                else if (j==1){
                    text = "" + yesterdayStats[i];
                    textView.setGravity(Gravity.CENTER);
                }
                else {
                    text = ""+totalStats[i];
                    textView.setGravity(Gravity.CENTER);
                }
                textView.setText(text);
                textView.setPadding(2,2,2,2);
                textView.setTextColor(ContextCompat.getColor(context,R.color.black));
                TableRow.LayoutParams layoutParams;

                if(j==0){
                    layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.3f);
                }
                else {
                    layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.1f);
                }
                textView.setLayoutParams(layoutParams);
                if(j==1 || j==4){
                    textView.setBackgroundResource(R.drawable.borderleft2);
                }
                else if (j==2 || j==5){
                    textView.setBackgroundResource(R.drawable.bordermiddle1);
                }

                tableRow.addView(textView);
            }
            tableLayoutNew.addView(tableRow);
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(tableLayoutNew);
        tableLayout.addView(scrollView);
        tableLayout.setBackgroundResource(R.drawable.border);
    }
}
