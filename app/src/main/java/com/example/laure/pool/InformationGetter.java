package com.example.laure.pool;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

    Data data = new Data();
    boolean isLive = false;
    Context context;
    Views views;

    public InformationGetter(Context context, Views views){
        this.context = context;
        this.views = views;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        data.getData(isLive);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(isLive){
            changeTitlesToLive(views);
        }

        createTable(data, 1,views.tableLayoutOverallStats);
        createTable(data, 2,views.tableLayoutTotalStats);
        createTable(data, 3,views.tableLayoutLastestStats);
        //poolersDataTablesManager.createTable(playersStats, 4, tableLayoutLastestBestPlayers);


    }


    /*
    Change textViews text is games are live
     */
    private void changeTitlesToLive(Views views){
        views.textViewOverallStatsTableSubtitle.setText("DIRECT");
        views.textViewLastestStatsTableTitle.setText("EN DIRECT");
        views.textViewLastestBestPlayersTableTitle.setText("MEILLEURS POINTEURS AUJOURD'HUI");
    }

    public void createTable(Data data, int tableNumber, TableLayout tableLayout){
        TableLayout tableLayoutNew = new TableLayout(context);

        for(int i=0; i<data.poolersData.nPoolers; i++){

            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            setTableRowBackground(data.poolersData.nPoolers, i,tableRow);

            selectTableRowFiller(data.poolersData, context, tableNumber,i,tableRow);

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

    private void selectTableRowFiller(PoolersData poolersData, Context context, int tableNumber, int i, TableRow tableRow){
        if(tableNumber==1){
            TableRowFiller.overallStatsTable(poolersData, context, i,tableRow);
        }
        else if(tableNumber==2){
            TableRowFiller.totalStatsTable(poolersData, context, i,tableRow);
        }
        else if(tableNumber==3){
            TableRowFiller.lastestStatsTable(poolersData, context, i,tableRow);
        }
    }

/*
    public void setYesterdayPlayersTable(){

        TableLayout tableLayoutNew = new TableLayout(context);

        sortYesterdayStats();

        for(int i=0; i<nPoolers; i++){

            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            setTableRowBackground(i,tableRow);

            fillTableRowYesterdayPlayers(i,tableRow);

            tableLayoutNew.addView(tableRow);
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(tableLayoutNew);
        tableLayoutYesterdayPlayers.addView(scrollView);
        tableLayoutYesterdayPlayers.setBackgroundResource(R.drawable.border);



    }
    */
/*
    public void fillTableRowYesterdayPlayers(int i,TableRow tableRow){

        for(int j=0; j<4; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            String text = "";

            if(j==0){
                text = "";
            }
            else if(j==1) {
                //text = " " + playersNames[i];
                textView.setGravity(Gravity.NO_GRAVITY);
            }
            else if (j==2){
                //text = "" + playersOwners[i];
            }
            else{
                //text = "" + playersPointsYesterday[i];
            }

            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            TableRow.LayoutParams layoutParams;

            if(j==0){
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
            }
            else if(j==1){
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.50f);
            }
            else if(j==2){
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
            }
            else {
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.10f);
            }

            textView.setLayoutParams(layoutParams);

            if(j==0){
                //textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==2){
                textView.setBackgroundResource(R.drawable.borderleft1);
            }
            else if (j==3){
                if(i!=nPoolers-1) {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumnlastrow);
                }
            }


            tableRow.addView(textView);
        }
    }

    public void getPlayersNames(Document document){

        String fullStringNames = document.select(".tooltip, .tooltipstered").text();
        String[] playersNamesSeparated = fullStringNames.split(" ");
        playersNames = new String[playersNamesSeparated.length/2];
        for(int i=0; i<playersNamesSeparated.length/2;i++){
            playersNamesSeparated[i*2] = playersNamesSeparated[i*2].toLowerCase();
            String firstName = playersNamesSeparated[i*2].substring(0,1).toUpperCase() + playersNamesSeparated[i*2].substring(1);
            playersNamesSeparated[i*2+1] = playersNamesSeparated[i*2+1].toLowerCase();
            String lastName = playersNamesSeparated[i*2+1].substring(0,1).toUpperCase() +
                    playersNamesSeparated[i*2+1].substring(1);
            playersNames[i] = firstName+ " " + lastName;
        }
    }
    */
}

