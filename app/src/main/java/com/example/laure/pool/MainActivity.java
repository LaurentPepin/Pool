package com.example.laure.pool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Views views = new Views();

        views.tableLayoutOverallStats = (TableLayout) findViewById(R.id.tableLayoutOverallStats);
        views.tableLayoutTotalStats = (TableLayout) findViewById(R.id.tableLayoutTotalStats);
        views.tableLayoutLiveStats = (TableLayout) findViewById(R.id.tableLayoutLiveStats);
        views.tableLayoutYesterdayStats = (TableLayout) findViewById(R.id.tableLayoutYesterdayStats);
        views.tableLayoutLiveBestPlayers = (TableLayout) findViewById(R.id.tableLayoutLiveBestPlayers);
        views.tableLayoutYesterdayBestPlayers = (TableLayout) findViewById(R.id.tableLayoutYesterdayBestPlayers);

        views.textViewOverallStatsTableSubtitle = (TextView) findViewById(R.id.textViewOverallStatsTableSubtitle);



        BackgroundWorker backgroundWorker = new BackgroundWorker(this,views);
        backgroundWorker.execute();
    }
    /*
    @Override
    protected void onRestart() {
        super.onRestart();
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        TextView textView = (TextView) findViewById(R.id.textViewDay);
        tableLayout.removeAllViews();
        InformationGetter informationGetter = new InformationGetter(this,tableLayout, textView);
        informationGetter.execute();
    }
    */
}
