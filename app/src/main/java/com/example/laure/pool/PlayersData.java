package com.example.laure.pool;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by laure on 2017-01-10.
 */
public class PlayersData {

    //PUBLIC ATTRIBUTES ///////////////////////////////////////////////////////////////////////////
    String[] liveBestPlayers;
    String[] liveBestPlayersPoolers;
    int[] liveBestPlayersG;
    int[] liveBestPlayersP;
    int[] liveBestPlayersPTS;


    String[] yesterdayBestPlayers;
    String[] yesterdayBestPlayersPoolers;
    int[] yesterdayBestPlayersPTS;

    String[] totalBestPlayers;
    String[] totalBestPlayersPoolers;
    int[] totalBestPlayersPTS;

    String[] freeAgentsPlayers;
    String[] freeAgentsPosition;
    int[] freeAgentsPTS;


    /*
    Constructor
     */
    public PlayersData(Document document, int nPoolers, boolean isLive){
        getPlayersNames(document,nPoolers,isLive);
        if(isLive){
            getPoolersLiveNames(document,nPoolers);
            getPlayersLiveStats(document,nPoolers);
        }
        getYesterdayPoolersAndPoints(document,nPoolers,isLive);

    }



    //PRIVATE METHODS //////////////////////////////////////////////////////////////////////////////

    private void getYesterdayPoolersAndPoints(Document document, int nPoolers, boolean isLive){
        Element element;
        if(isLive) {
            element = document.select(".tooltip, .tooltipstered").get(nPoolers).parent().parent().parent();
        }
        else{
            element = document.select(".tooltip, .tooltipstered").first().parent().parent().parent();
        }
        //Get poolers names
        yesterdayBestPlayersPoolers = new String[nPoolers];
        String poolersNames = document.select(element.cssSelector() + " .t10g").text();
        String[] poolersNamesDiv = poolersNames.split(" ");
        for(int i=0; i<nPoolers; i++){
            yesterdayBestPlayersPoolers[i] = formatStringToNormal(poolersNamesDiv[i*2]);
        }

        //Get points
        yesterdayBestPlayersPTS = new int[nPoolers];
        String playersPts = document.select(element.cssSelector() + " .t12nc").text();
        String[] ptsDiv = playersPts.split(" ");
        for(int i=0; i<nPoolers; i++){
            yesterdayBestPlayersPTS[i] = getIntegerValue(ptsDiv[i]);
        }

    }

    private void getPlayersLiveStats(Document document, int nPoolers){

        Element element = document.select(".tooltip, .tooltipstered").first().parent().parent().parent();
        String data = document.select(element.cssSelector() + " .t12nc").text();
        String[] dataDiv = data.split(" ");
        liveBestPlayersG = new int[nPoolers];
        liveBestPlayersP = new int[nPoolers];
        liveBestPlayersPTS = new int[nPoolers];
        for(int i=0; i< nPoolers; i++){
            liveBestPlayersG[i] = getIntegerValue(dataDiv[i*3]);
            Log.e("TAG", ""+liveBestPlayersG[i]);
            liveBestPlayersP[i] = getIntegerValue(dataDiv[i*3+1]);
            liveBestPlayersPTS[i] = getIntegerValue(dataDiv[i*3+2]);
        }
    }

    private void getPoolersLiveNames(Document document, int nPoolers){

        Element element = document.select(".tooltip, .tooltipstered").first().parent().parent().parent();
        liveBestPlayersPoolers = new String[nPoolers];
        String name;
        for(int i=0; i<nPoolers; i++){
            name = document.select(element.cssSelector() + " .t10g").get(i).text();
            if(name.equals("")){
                liveBestPlayersPoolers[i] = "";
            }
            else {
                String[] names = name.split(" ");
                liveBestPlayersPoolers[i] = formatStringToNormal(names[0]);
            }
        }
    }

    private void getPlayersNames(Document document, int nPoolers, boolean isLive){

        String fullStringNames = document.select(".tooltip, .tooltipstered").text();
        String[] playersNamesSeparated = fullStringNames.split(" ");
        String[] playersNames = new String[playersNamesSeparated.length/2];
        for(int i=0; i<playersNamesSeparated.length/2;i++){
            String firstName = formatStringToNormal(playersNamesSeparated[i*2]);
            String lastName = formatStringToNormal(playersNamesSeparated[i*2+1]);
            playersNames[i] = firstName+ " " + lastName;
        }
        liveBestPlayers = new String[nPoolers];
        yesterdayBestPlayers = new String[nPoolers];
        totalBestPlayers = new String[15];
        freeAgentsPlayers = new String[15];
        int factor = nPoolers;
        if(isLive){
            for(int i=0; i<nPoolers; i++){
                liveBestPlayers[i] = playersNames[i];
            }
        }
        else {
            factor = 0;
        }
        for(int i=0; i<nPoolers; i++){
            yesterdayBestPlayers[i] = playersNames[i+factor];
        }

        for(int i=0; i<15; i++){
            totalBestPlayers[i] = playersNames[i+factor+nPoolers];
        }

        for(int i=0; i<15; i++){
            freeAgentsPlayers[i] = playersNames[i+factor+nPoolers+15];
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
