package com.example.laure.pool;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * Created by laure on 2017-01-07.
 */
public class TableRowFiller {

    //PRIVATE ATTRIBUTES ///////////////////////////////////////////////////////////////////////////
    private static int[] liveGPOrdered;
    private static int[] livePTSOrdered;
    private static String[] livePoolersOrdered;
    private static int[] yesterdayGPOrdered;
    private static int[] yesterdayPTSOrdered;
    private static String[] yesterdayPoolersOrdered;


    //PUBLIC METHODS ///////////////////////////////////////////////////////////////////////////////
    public static void overallStatsTable(PoolersData poolersData, Context context, int i, TableRow tableRow, boolean isLive){

        for(int j=0; j<8; j++) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            String text;
            TableRow.LayoutParams layoutParams;
            layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f);
            if (j == 0) {
                text = "" + Integer.toString(i + 1);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.05f);
                textView.setBackgroundResource(R.drawable.borderright1);
            } else if (j == 1) {
                text = " " + poolersData.poolersNames[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f);
            } else if (j == 2) {
                if(isLive){
                    text = "" + poolersData.liveGP[i];
                }
                else {
                    text = "" + poolersData.yesterdayGP[i];
                }
                textView.setBackgroundResource(R.drawable.borderleft2);
            } else if (j == 3) {
                if(isLive) {
                    text = "" + poolersData.livePTS[i];
                }
                else {
                    text = "" + poolersData.yesterdayPTS[i];
                }
                textView.setBackgroundResource(R.drawable.bordermiddle1);
            } else if (j == 4) {
                if(isLive) {
                    if (poolersData.liveGP[i]!=0) {
                        double moy = poolersData.livePTS[i] * 1.0f / poolersData.liveGP[i];
                        text = String.format("%.2f", moy);
                    } else {
                        text = "0.00";
                    }
                }
                else {
                    if (poolersData.yesterdayGP[i]!=0) {
                        double moy = poolersData.yesterdayPTS[i] * 1.0f / poolersData.yesterdayGP[i];
                        text = String.format("%.2f", moy);
                    } else {
                        text = "0.00";
                    }
                }
            } else if (j == 5) {
                text = "" + poolersData.totalGP[i];
                textView.setBackgroundResource(R.drawable.borderleft2);
            } else if (j == 6) {
                text = "" + poolersData.totalPTS[i];
                if (i != poolersData.nPoolers - 1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                } else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            } else {
                if(poolersData.totalGP[i]!=0) {
                    double moy = poolersData.totalPTS[i] * 1.0f / poolersData.totalGP[i];
                    text = String.format("%.2f", moy);
                }
                else {
                    text = "0.00";
                }
            }
            textView.setText(text);
            textView.setPadding(2, 2, 2, 2);
            textView.setTextColor(ContextCompat.getColor(context, R.color.black));
            textView.setLayoutParams(layoutParams);
            tableRow.addView(textView);
        }
    }

    public static void totalStatsTable(PoolersData poolersData, Context context, int i, TableRow tableRow){
        for(int j=0; j<6; j++) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            String text;
            TableRow.LayoutParams layoutParams;
            layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.15f);
            if (j == 0) {
                text = "" + Integer.toString(i + 1);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.05f);
                textView.setBackgroundResource(R.drawable.borderright1);
            } else if (j == 1) {
                text = " " + poolersData.poolersNames[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.35f);
            } else if (j == 2) {
                text = "" + poolersData.totalGP[i];
                textView.setBackgroundResource(R.drawable.borderleft2);
            } else if (j == 3) {
                text = "" + poolersData.totalPTS[i];
                if (i != poolersData.nPoolers - 1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                } else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            } else if (j == 4) {
                if(poolersData.totalGP[i]!=0) {
                    double moy = poolersData.totalPTS[i] * 1.0f / poolersData.totalGP[i];
                    text = String.format("%.2f", moy);
                }
                else {
                    text = "0.00";
                }

            } else {
                int dif = poolersData.totalPTS[0] - poolersData.totalPTS[i];
                if (dif == 0) {
                    text = "-";
                } else {
                    text = "+" + Integer.toString(dif);
                }
                textView.setBackgroundResource(R.drawable.borderleft1);
            }
            textView.setText(text);
            textView.setPadding(2, 2, 2, 2);
            textView.setTextColor(ContextCompat.getColor(context, R.color.black));
            textView.setLayoutParams(layoutParams);
            tableRow.addView(textView);
        }
    }

    public static void liveStatsTable(PoolersData poolersData, Context context, int i, TableRow tableRow){

        if(i==0){
            sortLiveStats(poolersData);
        }

        for(int j=0; j<6; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            String text;
            TableRow.LayoutParams layoutParams;
            layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.15f);
            if(j==0){
                text = "" + Integer.toString(i+1);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
                textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==1) {
                text = " " + livePoolersOrdered[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
            }
            else if (j==2){
                text = "" + liveGPOrdered[i];
                textView.setBackgroundResource(R.drawable.borderleft2);
            }
            else if (j==3){
                text = "" + livePTSOrdered[i];
                if(i!=poolersData.nPoolers-1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            }
            else if (j==4){
                if(liveGPOrdered[i]!=0) {
                    double moy = livePTSOrdered[i] * 1.0f / liveGPOrdered[i];
                    text = String.format("%.2f", moy);
                }
                else {
                    text = "0.00";
                }

            }
            else {
                int dif = livePTSOrdered[0]- livePTSOrdered[i];
                if(dif==0){
                    text = "-";
                }
                else {
                    text = "+" + Integer.toString(dif);
                }
                textView.setBackgroundResource(R.drawable.borderleft1);
            }
            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            textView.setLayoutParams(layoutParams);
            tableRow.addView(textView);
        }
    }

    public static void yesterdayStatsTable(PoolersData poolersData, Context context, int i, TableRow tableRow, boolean isLive){

        if(isLive) {
            yesterdayPTSOrdered = poolersData.yesterdayPTS;
            yesterdayGPOrdered = poolersData.yesterdayGP;
            yesterdayPoolersOrdered = poolersData.yesterdayPoolersOrdered;
        }
        else {
            if(i==0) {
                sortYesterdayStats(poolersData);
            }
        }

        for(int j=0; j<6; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            String text;
            TableRow.LayoutParams layoutParams;
            layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.15f);
            if(j==0){
                text = "" + Integer.toString(i+1);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
                textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==1) {
                text = " " + yesterdayPoolersOrdered[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
            }
            else if (j==2){
                text = "" + yesterdayGPOrdered[i];
                textView.setBackgroundResource(R.drawable.borderleft2);
            }
            else if (j==3){
                text = "" + yesterdayPTSOrdered[i];
                if(i!=poolersData.nPoolers-1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            }
            else if (j==4){
                if(yesterdayGPOrdered[i]!=0) {
                    double moy = yesterdayPTSOrdered[i] * 1.0f / yesterdayGPOrdered[i];
                    text = String.format("%.2f", moy);
                }
                else {
                    text = "0.00";
                }

            }
            else {
                int dif = yesterdayPTSOrdered[0]- yesterdayPTSOrdered[i];
                if(dif==0){
                    text = "-";
                }
                else {
                    text = "+" + Integer.toString(dif);
                }
                textView.setBackgroundResource(R.drawable.borderleft1);
            }
            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            textView.setLayoutParams(layoutParams);
            tableRow.addView(textView);
        }
    }

    private static void sortYesterdayStats(PoolersData poolersData){

        yesterdayPTSOrdered = new int[poolersData.nPoolers];
        yesterdayGPOrdered = new int[poolersData.nPoolers];
        yesterdayPoolersOrdered = new String[poolersData.nPoolers];
        boolean[] done = new boolean[poolersData.nPoolers];

        for(int j=0; j<poolersData.nPoolers;j++) {
            int previousMax = 0;
            int maxIndex = 0;
            int previousGP = 100;
            for (int i = 0; i < poolersData.nPoolers; i++) {

                if (poolersData.yesterdayPTS[i] > previousMax && !done[i]) {
                    previousMax = poolersData.yesterdayPTS[i];
                    maxIndex = i;
                    previousGP = poolersData.yesterdayGP[i];
                }
                else if(poolersData.yesterdayPTS[i] >= previousMax && poolersData.yesterdayGP[i] < previousGP && !done[i]){
                    previousMax = poolersData.yesterdayPTS[i];
                    maxIndex = i;
                    previousGP = poolersData.yesterdayGP[i];
                }
            }
            done[maxIndex] = true;
            yesterdayPTSOrdered[j] = poolersData.yesterdayPTS[maxIndex];
            yesterdayGPOrdered[j] = poolersData.yesterdayGP[maxIndex];
            yesterdayPoolersOrdered[j] = poolersData.poolersNames[maxIndex];
        }
    }

    private static void sortLiveStats(PoolersData poolersData){

        livePTSOrdered = new int[poolersData.nPoolers];
        liveGPOrdered = new int[poolersData.nPoolers];
        livePoolersOrdered = new String[poolersData.nPoolers];
        boolean[] done = new boolean[poolersData.nPoolers];

        for(int j=0; j<poolersData.nPoolers;j++) {
            int previousMax = 0;
            int maxIndex = 0;
            int previousGP = 100;
            for (int i = 0; i < poolersData.nPoolers; i++) {
                if (poolersData.livePTS[i] > previousMax && !done[i]) {
                    previousMax = poolersData.livePTS[i];
                    maxIndex = i;
                    previousGP = poolersData.liveGP[i];
                }
                else if(poolersData.livePTS[i] >= previousMax && poolersData.liveGP[i] < previousGP && !done[i]){
                    previousMax = poolersData.livePTS[i];
                    maxIndex = i;
                    previousGP = poolersData.liveGP[i];
                }
            }
            done[maxIndex] = true;
            livePTSOrdered[j] = poolersData.livePTS[maxIndex];
            liveGPOrdered[j] = poolersData.liveGP[maxIndex];
            livePoolersOrdered[j] = poolersData.poolersNames[maxIndex];
        }
    }

    public static void bestLivePlayersTable(PlayersData playersData, Context context, int i, TableRow tableRow, int nPoolers){

        for(int j=0; j<6; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(2,2,2,2);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            String text;
            TableRow.LayoutParams layoutParams;
            layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.10f);
            if(j==0){
                text = "";
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
                textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==1) {
                text = " " + playersData.liveBestPlayers[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.45f);
            }
            else if (j==2){
                text = "" + playersData.liveBestPlayersPoolers[i];
                textView.setTextColor(ContextCompat.getColor(context,R.color.greyText));
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.20f);
                textView.setBackgroundResource(R.drawable.borderleft1);
            }
            else if (j==3){
                text = ""+ playersData.liveBestPlayersG[i];
                textView.setBackgroundResource(R.drawable.bordermiddle1);
            }
            else if(j==4){
                text = ""+playersData.liveBestPlayersP[i];
            }
            else{
                text = ""+playersData.liveBestPlayersPTS[i];
                if(i!=nPoolers-1) {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumnlastrow);
                }
            }
            textView.setLayoutParams(layoutParams);
            textView.setText(text);
            tableRow.addView(textView);
        }
    }

    public static void bestYesterdayPlayersTable(PlayersData playersData, Context context, int i, TableRow tableRow, int nPoolers){

        for(int j=0; j<4; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            String text;
            TableRow.LayoutParams layoutParams;

            if(j==0){
                text = "";
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
                textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==1) {
                text = " " + playersData.yesterdayBestPlayers[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.50f);
            }
            else if (j==2){
                textView.setBackgroundResource(R.drawable.borderleft1);
                text = "" + playersData.yesterdayBestPlayersPoolers[i];
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
                textView.setTextColor(ContextCompat.getColor(context,R.color.greyText));
            }
            else{
                text = "" + playersData.yesterdayBestPlayersPTS[i];
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.10f);
                if(i!=nPoolers-1) {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumnlastrow);
                }
            }

            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setLayoutParams(layoutParams);


            tableRow.addView(textView);
        }
    }

    public static void totalBestPicksTable(PlayersData playersData, Context context, int i, TableRow tableRow){
        for(int j=0; j<4; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            String text;
            TableRow.LayoutParams layoutParams;

            if(j==0){
                text = "";
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
                textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==1) {
                text = " " + playersData.totalBestPlayers[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.50f);
            }
            else if (j==2){
                textView.setBackgroundResource(R.drawable.borderleft1);
                text = "" + playersData.totalBestPlayersPoolers[i];
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
                textView.setTextColor(ContextCompat.getColor(context,R.color.greyText));
            }
            else{
                text = "" + playersData.totalBestPlayersPTS[i];
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.10f);
                if(i!=14) {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumnlastrow);
                }
            }

            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setLayoutParams(layoutParams);


            tableRow.addView(textView);
        }
    }

    public static void freeAgentsTable(PlayersData playersData, Context context, int i, TableRow tableRow){

        for(int j=0; j<4; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            String text;
            TableRow.LayoutParams layoutParams;

            if(j==0){
                text = "";
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
                textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==1) {
                text = " " + playersData.freeAgentsPlayers[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.50f);
            }
            else if (j==2){
                textView.setBackgroundResource(R.drawable.borderleft1);
                text = "" + playersData.freeAgentsPosition[i] ;
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
                textView.setTextColor(ContextCompat.getColor(context,R.color.greyText));
            }
            else{
                text = "" + playersData.freeAgentsPTS[i];
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.10f);
                if(i!=14) {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumnlastrow);
                }
            }

            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setLayoutParams(layoutParams);


            tableRow.addView(textView);
        }

    }

    public static void bestDaysRecordsTable(RecordsData recordsData, Context context, int i, TableRow tableRow){

        for(int j=0; j<5; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            String text;
            TableRow.LayoutParams layoutParams;

            if(j==0){
                text = "";
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
            }
            else if(j==1) {
                text = " " + recordsData.bestDays[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.40f);
                textView.setBackgroundResource(R.drawable.bordermiddle1);
            }
            else if (j==2){
                textView.setBackgroundResource(R.drawable.borderright1);
                text = "" + recordsData.bestDaysPoolers[i] ;
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
            }
            else if (j==3){
                text = "" + recordsData.bestDaysGP[i] ;
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.10f);
            }
            else {
                text = "" + recordsData.bestDaysPTS[i];
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.10f);
                if(i!=14) {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumnlastrow);
                }
            }

            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setLayoutParams(layoutParams);


            tableRow.addView(textView);
        }
    }

    public static void bestMonthsRecordsTable(RecordsData recordsData, Context context, int i, TableRow tableRow){

        for(int j=0; j<5; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            String text;
            TableRow.LayoutParams layoutParams;

            if(j==0){
                text = "";
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
            }
            else if(j==1) {
                text = " " + recordsData.bestMonths[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                textView.setBackgroundResource(R.drawable.bordermiddle1);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.40f);
            }
            else if (j==2){
                textView.setBackgroundResource(R.drawable.borderright1);
                text = "" + recordsData.bestMonthsPoolers[i] ;
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
            }
            else if (j==3){
                text = "" + recordsData.bestMonthsGP[i] ;
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.10f);
            }
            else {
                text = "" + recordsData.bestMonthsPTS[i];
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.10f);
                if(i!=14) {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastcolumnlastrow);
                }
            }

            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setLayoutParams(layoutParams);


            tableRow.addView(textView);
        }

    }



}
