package com.example.laure.pool;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by laure on 2017-01-07.
 */
public class PoolersStatsTablesManager {

    //PRIVATE ATTRIBUTES ///////////////////////////////////////////////////////////////////////////
    protected Context context;
    private Document document;
    private boolean isLive;
    private TableRowFiller tableRowFiller;




    //PUBLIC METHODS ///////////////////////////////////////////////////////////////////////////////
    public PoolersStatsTablesManager (Context context, boolean isLive){
        this.context = context;
        this.isLive = isLive;
    }

    public void gatherPoolersStats (PoolersStats poolersStats, Document document){
        this.document = document;
        Element websiteGeneralTable = selectMainTable(document);
        getPoolersNames(websiteGeneralTable, poolersStats);
        getPoolersStats(websiteGeneralTable, poolersStats);
    }

    public void createTable(PoolersStats poolersStats, int tableNumber, TableLayout tableLayout){
        tableRowFiller = new TableRowFiller();
        TableLayout tableLayoutNew = new TableLayout(context);

        for(int i=0; i<poolersStats.nPoolers; i++){

            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            setTableRowBackground(poolersStats.nPoolers, i,tableRow);

            selectTableRowFiller(poolersStats, context, tableNumber,i,tableRow);

            tableLayoutNew.addView(tableRow);
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(tableLayoutNew);
        tableLayout.addView(scrollView);
        tableLayout.setBackgroundResource(R.drawable.border);
    }


    //PRIVATE METHODS //////////////////////////////////////////////////////////////////////////////

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

    /*
    Select the table in the website containing all the stats of the poolers
     */
    private Element selectMainTable(Document document){
        if(isLive){
            //findNPoolersLive(document);
            return document.select(".titre").get(1).parent().parent().parent().parent();
        }
        return document.select(".t12b_n").first().parent().parent().parent();
    }

    /*
    Get all the poolers first names and sets the number of poolers (nPoolers)
     */
    private void getPoolersNames(Element websiteGeneralTable, PoolersStats poolersStats){
        String allNames;
        if(isLive){
            Element live = document.select(".titre").first().parent();
            Element tableLive = document.select(live.cssSelector() + " .t10gc").first().parent().parent();
            allNames = document.select(tableLive.cssSelector() + " .t12b_n").text();
        }
        else {
            allNames = document.select(websiteGeneralTable.cssSelector() + " .t12b_n").text();
        }
        String[] separatedNames = allNames.split(" ");
        poolersStats.nPoolers = separatedNames.length / 2;
        String[] poolersNames = new String[poolersStats.nPoolers];
        for(int i=0; i<poolersStats.nPoolers; i++){
            poolersNames[i] = separatedNames[i*2];
            poolersNames[i] = formatStringToNormal(poolersNames[i]);
        }
        poolersStats.poolersNames = poolersNames;
    }

    /*
    Transform the current word to the format:
     capital letter only for the first letter
     */
    private String formatStringToNormal(String string){
        string = string.toLowerCase();
        string = string.substring(0,1).toUpperCase() + string.substring(1);
        int index = string.indexOf('-');
        if(index>0){
            string = string.substring(0,index+1) + string.substring(index+1,index+2).toUpperCase() + ".";
        }
        return string;
    }

    /*
    Get all the stats of the poolers
     */
    private void getPoolersStats(Element websiteGeneralTable, PoolersStats poolersStats){
        String statistics = document.select(websiteGeneralTable.cssSelector() + " .t12nc").text();
        String[] separatedStats = statistics.split(" ");

        poolersStats.lastestGP = new int[poolersStats.nPoolers];
        poolersStats.lastestPTS = new int[poolersStats.nPoolers];
        poolersStats.totalGP = new int[poolersStats.nPoolers];
        poolersStats.totalPTS = new int[poolersStats.nPoolers];

        for(int i=0; i<poolersStats.nPoolers; i++){
            poolersStats.lastestGP[i] = getIntegerValue(separatedStats[i*4]);
            poolersStats.lastestPTS[i] = getIntegerValue(separatedStats[i*4+1]);
            poolersStats.totalGP[i] = getIntegerValue(separatedStats[i*4+2]);
            poolersStats.totalPTS[i] = getIntegerValue(separatedStats[i*4+3]);
        }
    }

    /*
    Get integer value of string and give 0 if "-"
     */
    private int getIntegerValue(String string){
        if(string.equals("-")) {
            return 0;
        }
        return Integer.parseInt(string);
    }


    private void selectTableRowFiller(PoolersStats poolersStats, Context context, int tableNumber, int i, TableRow tableRow){
        if(tableNumber==1){
            tableRowFiller.overallStatsTable(poolersStats, context, i,tableRow);
        }
        else if(tableNumber==2){
            tableRowFiller.totalStatsTable(poolersStats, context, i,tableRow);
        }
        else if(tableNumber==3){
            tableRowFiller.lastestStatsTable(poolersStats, context, i,tableRow);
        }
    }
}


