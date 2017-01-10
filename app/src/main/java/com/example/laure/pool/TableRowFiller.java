package com.example.laure.pool;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.nodes.Document;

/**
 * Created by laure on 2017-01-07.
 */
public class TableRowFiller {

    //PRIVATE ATTRIBUTES ///////////////////////////////////////////////////////////////////////////
    private int[] lastestGPOrdered;
    private int[] lastestPTSOrdered;
    private String[] lastestPoolersOrdered;



    public void overallStatsTable(PoolersStats poolersStats, Context context, int i, TableRow tableRow){
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
                text = " " + poolersStats.poolersNames[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f);
            } else if (j == 2) {
                text = "" + poolersStats.lastestGP[i];
                textView.setBackgroundResource(R.drawable.borderleft2);
            } else if (j == 3) {
                text = "" + poolersStats.lastestPTS[i];
                textView.setBackgroundResource(R.drawable.bordermiddle1);
            } else if (j == 4) {
                if(poolersStats.lastestGP[i]!=0) {
                    double moy = poolersStats.lastestPTS[i] * 1.0f / poolersStats.lastestGP[i];
                    text = String.format("%.2f", moy);
                }
                else {
                    text = "0.00";
                }
            } else if (j == 5) {
                text = "" + poolersStats.totalGP[i];
                textView.setBackgroundResource(R.drawable.borderleft2);
            } else if (j == 6) {
                text = "" + poolersStats.totalPTS[i];
                if (i != poolersStats.nPoolers - 1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                } else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            } else {
                if(poolersStats.totalGP[i]!=0) {
                    double moy = poolersStats.totalPTS[i] * 1.0f / poolersStats.totalGP[i];
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

    public void totalStatsTable(PoolersStats poolersStats, Context context, int i, TableRow tableRow){
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
                text = " " + poolersStats.poolersNames[i];
                textView.setGravity(Gravity.NO_GRAVITY);
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.35f);
            } else if (j == 2) {
                text = "" + poolersStats.totalGP[i];
                textView.setBackgroundResource(R.drawable.borderleft2);
            } else if (j == 3) {
                text = "" + poolersStats.totalPTS[i];
                if (i != poolersStats.nPoolers - 1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                } else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            } else if (j == 4) {
                if(poolersStats.totalGP[i]!=0) {
                    double moy = poolersStats.totalPTS[i] * 1.0f / poolersStats.totalGP[i];
                    text = String.format("%.2f", moy);
                }
                else {
                    text = "0.00";
                }

            } else {
                int dif = poolersStats.totalPTS[0] - poolersStats.totalPTS[i];
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

    public void lastestStatsTable(PoolersStats poolersStats, Context context, int i, TableRow tableRow){

        sortYesterdayStats(poolersStats);

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
                if(i!=poolersStats.nPoolers-1) {
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

    private void sortYesterdayStats(PoolersStats poolersStats){

        lastestPTSOrdered = new int[poolersStats.nPoolers];
        lastestGPOrdered = new int[poolersStats.nPoolers];
        lastestPoolersOrdered = new String[poolersStats.nPoolers];
        boolean[] done = new boolean[poolersStats.nPoolers];

        for(int j=0; j<poolersStats.nPoolers;j++) {
            int previousMax = 0;
            int maxIndex = 0;
            for (int i = 0; i < poolersStats.nPoolers; i++) {
                if (poolersStats.lastestPTS[i] > previousMax && !done[i]) {
                    previousMax = poolersStats.lastestPTS[i];
                    maxIndex = i;
                }
                else if(poolersStats.lastestPTS[i] >= previousMax && !done[i]){
                    previousMax = poolersStats.lastestPTS[i];
                    maxIndex = i;
                }
            }
            done[maxIndex] = true;
            lastestPTSOrdered[j] = poolersStats.lastestPTS[maxIndex];
            lastestGPOrdered[j] = poolersStats.lastestGP[maxIndex];
            lastestPoolersOrdered[j] = poolersStats.poolersNames[maxIndex];


        }
    }


}
