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

        TableLayout tableLayoutOverallStats = (TableLayout) findViewById(R.id.tableLayoutOverallStats);
        TableLayout tableLayoutTotalStats = (TableLayout) findViewById(R.id.tableLayoutTotalStats);
        TableLayout tableLayoutLastestStats = (TableLayout) findViewById(R.id.tableLayoutLastestStats);
        //TableLayout tableLayoutYesterdayPlayers = (TableLayout) findViewById(R.id.tableLayoutYesterdayPlayersTable);

        TextView textViewOverallStatsTableSubtitle = (TextView) findViewById(R.id.textViewOverallStatsTableSubtitle);
        TextView textViewLastestStatsTableTitle = (TextView) findViewById(R.id.textViewLastestStatsTableTitle);
        TextView textViewLastestBestPlayersTableTitle = (TextView) findViewById(R.id.textViewLastestBestPlayersTableTitle);

        InformationGetter informationGetter = new InformationGetter(this,tableLayoutOverallStats, tableLayoutTotalStats, tableLayoutLastestStats,
                textViewOverallStatsTableSubtitle, textViewLastestStatsTableTitle, textViewLastestBestPlayersTableTitle);
        informationGetter.execute();
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
