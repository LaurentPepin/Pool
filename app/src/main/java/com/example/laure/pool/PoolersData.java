package com.example.laure.pool;


import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by laure on 2017-01-10.
 */
public class PoolersData {

    //PUBLIC ATTRIBUTES ///////////////////////////////////////////////////////////////////////////
    int nPoolers;
    String[] poolersNames;
    int[] liveGP;
    int[] livePTS;
    int[] yesterdayGP;
    int[] yesterdayPTS;
    int[] totalGP;
    int[] totalPTS;

    String[] yesterdayPoolersOrdered;

    /*
    Constructor
     */
    public PoolersData(Document document, boolean isLive){

        Element websiteGeneralTable = selectMainTable(document, isLive);
        getPoolersNames(document, websiteGeneralTable, isLive);
        getPoolersData(document, websiteGeneralTable, isLive);
    }




    //PRIVATE METHODS //////////////////////////////////////////////////////////////////////////////

    /*
    Select the table in the website containing all the stats of the poolers
     */
    private Element selectMainTable(Document document, boolean isLive){
        if(isLive){
            return document.select(".titre").get(1).parent().parent().parent().parent();
        }
        return document.select(".t12b_n").first().parent().parent().parent();
    }

    /*
    Get all the poolers first names and sets the number of poolers (nPoolers)
     */

    private void getPoolersNames(Document document, Element websiteGeneralTable, boolean isLive){
        String allNames;
        String[] separatedNames;
        if(isLive){
            Element live = document.select(".titre").first().parent();
            Element tableLive = document.select(live.cssSelector() + " .t10gc").first().parent().parent();
            allNames = document.select(tableLive.cssSelector() + " .t12b_n").text();
            separatedNames = allNames.split(" ");
            nPoolers = separatedNames.length / 2;
            allNames = document.select(websiteGeneralTable.cssSelector() + " .t12b_n").text();
            separatedNames = allNames.split(" ");
        }
        else {
            allNames = document.select(websiteGeneralTable.cssSelector() + " .t12b_n").text();
            separatedNames = allNames.split(" ");
            nPoolers = separatedNames.length / 2;
        }
        String[] poolersNames = new String[nPoolers];
        for(int i=0; i<nPoolers; i++){
            poolersNames[i] = separatedNames[i*2];
            poolersNames[i] = formatStringToNormal(poolersNames[i]);
        }
        this.poolersNames = poolersNames;
    }

    /*
    Get all the stats of the poolers
     */
    private void getPoolersData(Document document, Element websiteGeneralTable, boolean isLive){
        String statistics = document.select(websiteGeneralTable.cssSelector() + " .t12nc").text();
        String[] separatedStats = statistics.split(" ");
        if(isLive){
            liveGP = new int[nPoolers];
            livePTS = new int[nPoolers];
        }
        yesterdayGP = new int[nPoolers];
        yesterdayPTS = new int[nPoolers];
        totalGP = new int[nPoolers];
        totalPTS = new int[nPoolers];

        for(int i=0; i<nPoolers; i++){
            if(isLive){
                liveGP[i] = getIntegerValue(separatedStats[i*4]);
                livePTS[i] = getIntegerValue(separatedStats[i*4+1]);
                getYesterdayPoolersData(document);
                //yesterdayGP[i] = liveGP[i];
                //yesterdayPTS[i] = livePTS[i];
            }
            else{
                yesterdayGP[i] = getIntegerValue(separatedStats[i*4]);
                yesterdayPTS[i] = getIntegerValue(separatedStats[i*4+1]);
            }
            totalGP[i] = getIntegerValue(separatedStats[i*4+2]);
            totalPTS[i] = getIntegerValue(separatedStats[i*4+3]);
        }
    }


    private void getYesterdayPoolersData(Document document){
        Element yesterdayTable = document.select(".t24b_bl").get(6).parent().parent().parent();
        String poolersData = document.select(yesterdayTable.cssSelector() + " .t12nc").text();
        String[] stringsData = poolersData.split(" ");
        for(int i=0; i<nPoolers; i++){
            yesterdayGP[i] = getIntegerValue(stringsData[i*2]);
            yesterdayPTS[i] = getIntegerValue(stringsData[i*2+1]);
        }
        yesterdayPoolersOrdered = new String[nPoolers];
        String poolersNames = document.select(yesterdayTable.cssSelector() + " .t12b_n").text();
        String[] poolersNamesSeparated = poolersNames.split(" ");
        for(int i=0; i<nPoolers; i++){
            poolersNamesSeparated[i] = poolersNamesSeparated[i*2];
            poolersNamesSeparated[i] = formatStringToNormal(poolersNamesSeparated[i]);
            yesterdayPoolersOrdered[i] = poolersNamesSeparated[i];
        }
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
    Get integer value of string and give 0 if "-"
     */
    private int getIntegerValue(String string){
        if(string.equals("-")) {
            return 0;
        }
        return Integer.parseInt(string);
    }

}
