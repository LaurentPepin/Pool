package com.example.laure.pool;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by laure on 2017-01-13.
 */
public class RecordsData {

    String[] bestDays;
    String[] bestDaysPoolers;
    String[] bestDaysGP;
    String[] bestDaysPTS;

    String[] bestMonths;
    String[] bestMonthsPoolers;
    String[] bestMonthsGP;
    String[] bestMonthsPTS;


    public RecordsData(Document document){

        getBestDaysData(document);
        getBestMonthsData(document);


    }


    //PRIVATE METHODS //////////////////////////////////////////////////////////////////////////////

    private void getBestMonthsData(Document document){

        Element element = document.select(".eel").last().parent().parent();
        bestMonths = new String[15];
        bestMonthsPoolers = new String[15];
        bestMonthsGP = new String[15];
        bestMonthsPTS = new String[15];
        for(int i=0; i<15;i++){
            bestMonths[i] = formatStringToNormal(document.select(element.cssSelector() + " .t12gl").get(i).text());
            bestMonthsPoolers[i] = getFirstNameNormal(document.select(element.cssSelector() + " .t12b_n").get(i).text());
            bestMonthsGP[i] = document.select(element.cssSelector() + " .t12nc").get(i*2).text();
            bestMonthsPTS[i] = document.select(element.cssSelector() + " .t12nc").get(i*2+1).text();
        }
    }

    private void getBestDaysData(Document document){

        Element element = document.select(".eel").last().parent().parent().parent().parent().parent();
        bestDays = new String[15];
        bestDaysPoolers = new String[15];
        bestDaysGP = new String[15];
        bestDaysPTS = new String[15];
        for(int i=0; i<15;i++){
            bestDays[i] = formatStringToNormal(document.select(element.cssSelector() + " .t12gl").get(i).text());
            bestDaysPoolers[i] = getFirstNameNormal(document.select(element.cssSelector() + " .t12b_n").get(i).text());
            bestDaysGP[i] = document.select(element.cssSelector() + " .t12nc").get(i*2).text();
            bestDaysPTS[i] = document.select(element.cssSelector() + " .t12nc").get(i*2+1).text();
        }
    }


    private String getFirstNameNormal (String string){
        String[] names = string.split(" ");
        return formatStringToNormal(names[0]);
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





}
