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
    }


    /*
    Change textViews text is games are live
     */
    private void changeTitlesToLive(Views views){
        views.textViewOverallStatsTableSubtitle.setText("Direct");
    }

    public void createTable(Data data, int tableNumber, TableLayout tableLayout){
        TableLayout tableLayoutNew = new TableLayout(context);

        for(int i=0; i<data.poolersData.nPoolers; i++){

            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            setTableRowBackground(data.poolersData.nPoolers, i,tableRow);

            selectTableRowFiller(data, context, tableNumber,i,tableRow);

            tableLayoutNew.addView(tableRow);
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(tableLayoutNew);
        tableLayout.addView(scrollView);
        tableLayout.setBackgroundResource(R.drawable.border);
    }

    private  void setTableRowBackground(int nPoolers, int i, TableRow tableRow){
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
    }


/*



    */
}

