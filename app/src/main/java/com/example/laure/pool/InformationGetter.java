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


    boolean isLive = false;
    String TAG = "InformationGetter";
    Context context;

    TableLayout tableLayoutOverallStats;
    TableLayout tableLayoutTotalStats;
    TableLayout tableLayoutLastestStats;

    TextView textViewOverallStatsTableSubtitle;
    TextView textViewLastestStatsTableTitle;
    TextView textViewLastestBestPlayersTableTitle;

    PoolersStats poolersStats = new PoolersStats();

    GeneralFunctions generalFunctions;
    Document document;
    PoolersStatsTablesManager poolersStatsTablesManager;

    public InformationGetter(Context context,
                             TableLayout tableLayoutOverallStats,
                             TableLayout tableLayoutTotalStats,
                             TableLayout tableLayoutLastestStats,
                             TextView textViewOverallStatsTableSubtitle,
                             TextView textViewLastestStatsTableTitle,
                             TextView textViewLastestBestPlayersTableTitle
                             ){
        this.context = context;
        this.tableLayoutOverallStats = tableLayoutOverallStats;
        this.tableLayoutTotalStats = tableLayoutTotalStats;
        this.tableLayoutLastestStats = tableLayoutLastestStats;
        this.textViewOverallStatsTableSubtitle = textViewOverallStatsTableSubtitle;
        this.textViewLastestStatsTableTitle = textViewLastestStatsTableTitle;
        this.textViewLastestBestPlayersTableTitle = textViewLastestBestPlayersTableTitle;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            document = Jsoup.connect("https://www.marqueur.com/poolpepin2015").get();

            generalFunctions = new GeneralFunctions(document);

            isLive = generalFunctions.checkIfLive();

            poolersStatsTablesManager = new PoolersStatsTablesManager(context,isLive);
            poolersStatsTablesManager.gatherPoolersStats(poolersStats, document);

        }
        catch (Exception e){e.printStackTrace();}

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(isLive){
            generalFunctions.changeTitlesToLive(textViewOverallStatsTableSubtitle, textViewLastestStatsTableTitle, textViewLastestBestPlayersTableTitle);
        }

        poolersStatsTablesManager = new PoolersStatsTablesManager(context, isLive);

        poolersStatsTablesManager.createTable(poolersStats, 1,tableLayoutOverallStats);
        poolersStatsTablesManager.createTable(poolersStats, 2,tableLayoutTotalStats);
        poolersStatsTablesManager.createTable(poolersStats, 3,tableLayoutLastestStats);




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

