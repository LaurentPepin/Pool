package com.example.laure.pool;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
public class BackgroundWorker extends AsyncTask<Void,Void,Void> {

    Data data = new Data();
    boolean isLive = false;
    Context context;
    Views views;

    public BackgroundWorker(Context context, Views views){
        this.context = context;
        this.views = views;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        isLive = data.getData();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(isLive){
            changeTitlesToLive(views);
        }

        createTable(data, 1, views.tableLayoutOverallStats);
        createTable(data, 2, views.tableLayoutTotalStats);
        if(isLive){
            createTable(data, 3, views.tableLayoutLiveStats);
            createTable(data, 4, views.tableLayoutLiveBestPlayers);
        }
        else {
            views.tableLayoutLiveStats.setVisibility(View.GONE);
            views.tableLayoutLiveBestPlayers.setVisibility(View.GONE);
        }
        createTable(data, 5, views.tableLayoutYesterdayStats);
        createTable(data, 6, views.tableLayoutYesterdayBestPlayers);
        createTable(data, 7, views.tableLayoutTotalBestPicks);
        createTable(data, 8, views.tableLayoutFreeAgents);
        createTable(data, 9, views.tableLayoutBestDaysRecords);
        createTable(data, 10, views.tableLayoutBestMonthsRecords);
    }


    /*
    Change textViews text is games are live
     */
    private void changeTitlesToLive(Views views){
        views.textViewOverallStatsTableSubtitle.setText("Direct");
    }

    public void createTable(Data data, int tableNumber, TableLayout tableLayout){
        TableLayout tableLayoutNew = new TableLayout(context);

        if(tableNumber < 7) {
            for (int i = 0; i < data.poolersData.nPoolers; i++) {

                TableRow tableRow = new TableRow(context);
                tableRow.setGravity(Gravity.CENTER);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

                setTableRowBackground((data.poolersData.nPoolers-1), i, tableRow);

                selectTableRowFiller(data, context, tableNumber, i, tableRow);

                tableLayoutNew.addView(tableRow);
            }
        }
        else {
            for (int i = 0; i < 15; i++) {

                TableRow tableRow = new TableRow(context);
                tableRow.setGravity(Gravity.CENTER);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

                setTableRowBackground(14, i, tableRow);

                selectTableRowFiller(data, context, tableNumber, i, tableRow);

                tableLayoutNew.addView(tableRow);
            }
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(tableLayoutNew);
        tableLayout.addView(scrollView);
        tableLayout.setBackgroundResource(R.drawable.border);
    }

    private  void setTableRowBackground(int lastRow, int i, TableRow tableRow){
        if(i%2==1){
            if(i==lastRow){
                tableRow.setBackgroundResource(R.drawable.lastrowodd);
            }
            else {
                tableRow.setBackgroundResource(R.drawable.rowodd);
            }
        }
        else{
            if(i==lastRow){
                tableRow.setBackgroundResource(R.drawable.lastroweven);
            }
            else {
                tableRow.setBackgroundResource(R.drawable.roweven);
            }
        }
    }

    private void selectTableRowFiller(Data data, Context context, int tableNumber, int i, TableRow tableRow){
        if(tableNumber==1){
            TableRowFiller.overallStatsTable(data.poolersData, context, i,tableRow, isLive);
        }
        else if(tableNumber==2){
            TableRowFiller.totalStatsTable(data.poolersData, context, i,tableRow);
        }
        else if(tableNumber==3){
            TableRowFiller.liveStatsTable(data.poolersData, context, i,tableRow);
        }
        else if(tableNumber==4){
            TableRowFiller.bestLivePlayersTable(data.playersData, context, i, tableRow, data.poolersData.nPoolers);
        }
        else if(tableNumber==5){
            TableRowFiller.yesterdayStatsTable(data.poolersData, context, i,tableRow, isLive);
        }
        else if(tableNumber ==6){
            TableRowFiller.bestYesterdayPlayersTable(data.playersData, context, i, tableRow, data.poolersData.nPoolers);
        }
        else if(tableNumber == 7){
            TableRowFiller.totalBestPicksTable(data.playersData, context, i, tableRow);
        }
        else if(tableNumber == 8){
            TableRowFiller.freeAgentsTable(data.playersData, context, i, tableRow);
        }
        else if(tableNumber == 9){
            TableRowFiller.bestDaysRecordsTable(data.recordsData, context, i, tableRow);
        }
        else if(tableNumber == 10){
            TableRowFiller.bestMonthsRecordsTable(data.recordsData, context, i, tableRow);
        }
    }


    private String formatStringToNormal (String string){
        int maxLenght = 40;
        String[] strings = string.split(" ");
        string = strings[0];
        string.toLowerCase();
        string = string.substring(0,1).toUpperCase() + string.substring(1);
        if(string.length() > maxLenght){
            int index = string.indexOf('-');
            if(index > 0){
                string = string.substring(0,index+1) + string.substring(index+1,index+2).toUpperCase() + ".";
            }
            else {
                string = string.substring(0,maxLenght-1);
            }
        }
        return string;
    }







    private String formadtStringToNormal(String string){
        string = string.toLowerCase();
        string = string.substring(0,1).toUpperCase() + string.substring(1);
        int index = string.indexOf('-');
        if(index>0){
            string = string.substring(0,index+1) + string.substring(index+1,index+2).toUpperCase() + ".";
        }
        return string;
    }




}

