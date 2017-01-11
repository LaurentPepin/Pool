package com.example.laure.pool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by laure on 2017-01-10.
 */
public class Data {

    //PUBLIC ATTRIBUTES & METHODS //////////////////////////////////////////////////////////////////
    PoolersData poolersData;

    public void getData(boolean isLive){
        try {
            Document document = Jsoup.connect("https://www.marqueur.com/poolpepin2015").get();
            isLive = checkIfLive(document);
            poolersData = new PoolersData(document, isLive);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //PRIVATE METHODS //////////////////////////////////////////////////////////////////////////////
    /*
    Return a boolean saying if there are games live (true) or not (false)
     */
    private boolean checkIfLive (Document document) {
        String title = document.select(".titre").first().text();
        return title.equals("EN DIRECT");
    }




}
