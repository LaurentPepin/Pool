package com.example.laure.pool;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TextView;

import org.jsoup.nodes.Document;

/**
 * Created by laure on 2017-01-07.
 */
public class GeneralFunctions {

    //Private attributes //////////////////////////////////////////////////////////////////////
    private Document document;

    //Public methods //////////////////////////////////////////////////////////////////////////

    /*
    Constructor
     */
    public GeneralFunctions(Document document){
        this.document = document;
    }

    /*
    Return a boolean saying if there are games live (true) or not (false)
     */
    public boolean checkIfLive () {
        String title = document.select(".titre").first().text();
        return title.equals("EN DIRECT");
    }

    /*
    Change textViews text is games are live
     */
    public void changeTitlesToLive(TextView textViewOverallTableSubtitle, TextView textViewLastestStatsTableTitle, TextView textViewLastestBestPlayersTableTitle){
        textViewOverallTableSubtitle.setText("DIRECT");
        textViewLastestStatsTableTitle.setText("EN DIRECT");
        textViewLastestBestPlayersTableTitle.setText("MEILLEURS POINTEURS AUJOURD'HUI");
    }









}
