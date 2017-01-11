package com.example.laure.pool;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * Created by laure on 2017-01-07.
 */
public class TableRowFiller {

    //PRIVATE ATTRIBUTES ///////////////////////////////////////////////////////////////////////////
    private static int[] lastestGPOrdered;
    private static int[] lastestPTSOrdered;
    private static String[] lastestPoolersOrdered;


    //PUBLIC METHODS ///////////////////////////////////////////////////////////////////////////////
    public static void overallStatsTable(PoolersData poolersData, Context context, int i, TableRow tableRow){
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
                text = "" + poolersData.lastestGP[i];
                textView.setBackgroundResource(R.drawable.borderleft2);
            } else if (j == 3) {
                text = "" + poolersData.lastestPTS[i];
                textView.setBackgroundResource(R.drawable.bordermiddle1);
            } else if (j == 4) {
                if(poolersData.lastestGP[i]!=0) {
                    double moy = poolersData.lastestPTS[i] * 1.0f / poolersData.lastestGP[i];
                    text = String.format("%.2f", moy);
                }
                else {
                    text = "0.00";
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

    public static void lastestStatsTable(PoolersData poolersData, Context context, int i, TableRow tableRow){

        sortYesterdayStats(poolersData);

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
                text = " " + lastestPoolersOrdered[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
            }
            else if (j==2){
                text = "" + lastestGPOrdered[i];
                textView.setBackgroundResource(R.drawable.borderleft2);
            }
            else if (j==3){
                text = "" + lastestPTSOrdered[i];
                if(i!=poolersData.nPoolers-1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            }
            else if (j==4){
                if(lastestGPOrdered[i]!=0) {
                    double moy = lastestPTSOrdered[i] * 1.0f / lastestGPOrdered[i];
                    text = String.format("%.2f", moy);
                }
                else {
                    text = "0.00";
                }

            }
            else {
                int dif = lastestPTSOrdered[0]- lastestPTSOrdered[i];
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

        lastestPTSOrdered = new int[poolersData.nPoolers];
        lastestGPOrdered = new int[poolersData.nPoolers];
        lastestPoolersOrdered = new String[poolersData.nPoolers];
        boolean[] done = new boolean[poolersData.nPoolers];

        for(int j=0; j<poolersData.nPoolers;j++) {
            int previousMax = 0;
            int maxIndex = 0;
            for (int i = 0; i < poolersData.nPoolers; i++) {
                if (poolersData.lastestPTS[i] > previousMax && !done[i]) {
                    previousMax = poolersData.lastestPTS[i];
                    maxIndex = i;
                }
                else if(poolersData.lastestPTS[i] >= previousMax && !done[i]){
                    previousMax = poolersData.lastestPTS[i];
                    maxIndex = i;
                }
            }
            done[maxIndex] = true;
            lastestPTSOrdered[j] = poolersData.lastestPTS[maxIndex];
            lastestGPOrdered[j] = poolersData.lastestGP[maxIndex];
            lastestPoolersOrdered[j] = poolersData.poolersNames[maxIndex];


        }
    }


}
