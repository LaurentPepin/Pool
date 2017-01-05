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
    TableLayout tableLayout;
    TableLayout tableLayoutTotal;
    TableLayout tableLayoutYesterday;
    TextView textView;
    String[] poolersNames;
    int nPoolers;
    int[] stats;
    Element mainTable;
    int[] yesterdayPtsOrdered;
    int[] yesterdayPjOrdered;
    String[] yesterdayPoolersOrdered;

    public InformationGetter(Context ctx,TableLayout tb, TextView tx, TableLayout tableLayoutTot, TableLayout tableLayoutY){
        context = ctx;
        tableLayout = tb;
        textView = tx;
        tableLayoutTotal = tableLayoutTot;
        tableLayoutYesterday = tableLayoutY;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect("https://www.marqueur.com/poolpepin2015").get();

            findIfLive(document);
            selectMainTable(document);
            getOverallInformation(document,mainTable);

        }
        catch (Exception e){e.printStackTrace();}

        return null;
    }

    public void selectMainTable(Document document){
        if(isLive){
            findNPoolersLive(document);
            mainTable = document.select(".titre").get(1).parent().parent().parent().parent();
        }
        else {
            mainTable = document.select(".t12b_n").first().parent().parent().parent();
        }
    }

    public void findIfLive(Document document){
        String title = document.select(".titre").first().text();
        if(title.equals("EN DIRECT")){
            isLive = true;
        }
    }

    public void findNPoolersLive (Document document){
        Element live = document.select(".titre").first().parent();
        Element tableLive = document.select(live.cssSelector() + " .t10gc").first().parent().parent();
        String text = document.select(tableLive.cssSelector() + " .t12b_n").text();
        String[] separatedNames = text.split(" ");
        nPoolers = separatedNames.length / 2;
    }

    public void getOverallInformation(Document document, Element element){
        getFullNames(document,element);
        getStats(document,element);
    }

    public void getFullNames (Document document, Element element){

        String stringPoolersNames = document.select(element.cssSelector() + " .t12b_n").text();
        String[] separatedNames = stringPoolersNames.split(" ");

        if(!isLive) {
            nPoolers = separatedNames.length / 2;
        }
        poolersNames = new String[nPoolers];
        for(int i=0; i<nPoolers; i++){
            //names[i] = strings[i*2] + " " + strings[i*2+1].substring(0,1) + ".";
            poolersNames[i] = separatedNames[i*2];
            int index = poolersNames[i].indexOf('-');
            if(index>0){
                poolersNames[i] = poolersNames[i].substring(0,index+2) + ".";
            }
        }
    }

    public void getStats(Document document, Element element){

        String statistics = document.select(element.cssSelector() + " .t12nc").text();
        String[] separatedStats = statistics.split(" ");

        stats = new int[nPoolers*4];

        for(int i=0; i<nPoolers*4; i++){
            if(!separatedStats[i].equals("-")) {
                stats[i] = Integer.parseInt(separatedStats[i]);
            }
            else {
                stats[i] = 0;
            }
        }
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(isLive){
            textView.setText(R.string.Direct);
        }

        setOverallInformationTable();

        setTotalInformationTable();

        setYesterdayInformationTable();


    }

    public void setOverallInformationTable(){
        TableLayout tableLayoutNew = new TableLayout(context);

        for(int i=0; i<nPoolers; i++){

            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            setTableRowBackground(i,tableRow);

            fillTableRow(i,tableRow);

            tableLayoutNew.addView(tableRow);
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(tableLayoutNew);
        tableLayout.addView(scrollView);
        tableLayout.setBackgroundResource(R.drawable.border);
    }

    public void setTableRowBackground(int i, TableRow tableRow){
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

    public void fillTableRow(int i,TableRow tableRow){

        for(int j=0; j<8; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            String text = "";

            if(j==0){
                text = "" + Integer.toString(i+1);
            }
            else if(j==1) {
                text = " " + poolersNames[i];
                textView.setGravity(Gravity.NO_GRAVITY);
            }
            else if (j==2){
                text = "" + stats[i*4];
            }
            else if (j==3){
                text = "" + stats[i*4+1];
            }
            else if (j==4){
                double moy = stats[i*4+1]*1.0f/stats[i*4];
                text = String.format("%.2f", moy);

            }
            else if (j==5){
                text = "" + stats[i*4+2];
            }
            else if (j==6){
                text = "" + stats[i*4+3];
            }
            else {
                double moy = stats[i*4+3]*1.0f/stats[i*4+2];
                text = String.format("%.2f", moy);
            }
            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            TableRow.LayoutParams layoutParams;

            if(j==0){
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
            }
            else if(j==1){
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.25f);
            }
            else {
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.1f);
            }
            textView.setLayoutParams(layoutParams);
            if(j==0){
                textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==2 || j==5){
                textView.setBackgroundResource(R.drawable.borderleft2);
            }
            else if (j==3){
                textView.setBackgroundResource(R.drawable.bordermiddle1);
            }
            else if(j==6){
                if(i!=nPoolers-1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            }

            tableRow.addView(textView);
        }
    }

    public void setTotalInformationTable(){
        TableLayout tableLayoutNew = new TableLayout(context);

        for(int i=0; i<nPoolers; i++){

            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            setTableRowBackground(i,tableRow);

            fillTableRowTotal(i,tableRow);

            tableLayoutNew.addView(tableRow);
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(tableLayoutNew);
        tableLayoutTotal.addView(scrollView);
        tableLayoutTotal.setBackgroundResource(R.drawable.border);
    }

    public void fillTableRowTotal(int i,TableRow tableRow){

        for(int j=0; j<6; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            String text = "";

            if(j==0){
                text = "" + Integer.toString(i+1);
            }
            else if(j==1) {
                text = " " + poolersNames[i];
                textView.setGravity(Gravity.NO_GRAVITY);
            }
            else if (j==2){
                text = "" + stats[i*4+2];
            }
            else if (j==3){
                text = "" + stats[i*4+3];
            }
            else if (j==4){
                double moy = stats[i*4+3]*1.0f/stats[i*4+2];
                text = String.format("%.2f", moy);

            }
            else {
                int dif = stats[3]- stats[i*4+3];
                if(dif==0){
                    text = "-";
                }
                else {
                    text = "+" + Integer.toString(dif);
                }
            }
            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            TableRow.LayoutParams layoutParams;

            if(j==0){
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
            }
            else if(j==1){
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
            }
            else {
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.15f);
            }

            textView.setLayoutParams(layoutParams);

            if(j==0){
                textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==2){
                textView.setBackgroundResource(R.drawable.borderleft2);
            }
            else if (j==3){
                if(i!=nPoolers-1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            }
            else if(j==5){
                textView.setBackgroundResource(R.drawable.borderleft1);
            }

            tableRow.addView(textView);
        }
    }

    public void setYesterdayInformationTable(){
        TableLayout tableLayoutNew = new TableLayout(context);

        sortYesterdayStats();

        for(int i=0; i<nPoolers; i++){

            TableRow tableRow = new TableRow(context);
            tableRow.setGravity(Gravity.CENTER);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            setTableRowBackground(i,tableRow);

            fillTableRowYesterday(i,tableRow);

            tableLayoutNew.addView(tableRow);
        }
        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(tableLayoutNew);
        tableLayoutYesterday.addView(scrollView);
        tableLayoutYesterday.setBackgroundResource(R.drawable.border);
    }

    public void fillTableRowYesterday(int i,TableRow tableRow){

        for(int j=0; j<6; j++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            String text = "";

            if(j==0){
                text = "" + Integer.toString(i+1);
            }
            else if(j==1) {
                text = " " + yesterdayPoolersOrdered[i];
                textView.setGravity(Gravity.NO_GRAVITY);
            }
            else if (j==2){
                text = "" + yesterdayPjOrdered[i];
            }
            else if (j==3){
                text = "" + yesterdayPtsOrdered[i];
            }
            else if (j==4){
                double moy = yesterdayPtsOrdered[i]*1.0f/yesterdayPjOrdered[i];
                text = String.format("%.2f", moy);

            }
            else {
                int dif = yesterdayPtsOrdered[0]- yesterdayPtsOrdered[i];
                if(dif==0){
                    text = "-";
                }
                else {
                    text = "+" + Integer.toString(dif);
                }
            }
            textView.setText(text);
            textView.setPadding(2,2,2,2);
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
            TableRow.LayoutParams layoutParams;

            if(j==0){
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.05f);
            }
            else if(j==1){
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.35f);
            }
            else {
                layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.15f);
            }

            textView.setLayoutParams(layoutParams);

            if(j==0){
                textView.setBackgroundResource(R.drawable.borderright1);
            }
            else if(j==2){
                textView.setBackgroundResource(R.drawable.borderleft2);
            }
            else if (j==3){
                if(i!=nPoolers-1) {
                    textView.setBackgroundResource(R.drawable.maincolumn);
                }
                else {
                    textView.setBackgroundResource(R.drawable.maincolumnlastrow);
                }
            }
            else if(j==5){
                textView.setBackgroundResource(R.drawable.borderleft1);
            }

            tableRow.addView(textView);
        }
    }

    public void sortYesterdayStats(){

        yesterdayPtsOrdered = new int[nPoolers];
        yesterdayPjOrdered = new int[nPoolers];
        yesterdayPoolersOrdered = new String[nPoolers];
        boolean[] done = new boolean[nPoolers];

        for(int j=0; j<nPoolers;j++) {
            int previousMax = 0;
            int maxIndex = 0;
            for (int i = 0; i < nPoolers; i++) {
                if (stats[i * 4 + 1] > previousMax && !done[i]) {

                    previousMax = stats[i * 4 + 1];
                    maxIndex = i;
                    Log.e(TAG, ""+maxIndex);
                }
            }
            done[maxIndex] = true;
            yesterdayPtsOrdered[j] = stats[maxIndex*4+1];
            yesterdayPjOrdered[j] = stats[maxIndex*4];
            yesterdayPoolersOrdered[j] = poolersNames[maxIndex];
        }
    }



}

