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
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        TextView textView = (TextView) findViewById(R.id.textViewDay);

        InformationGetter informationGetter = new InformationGetter(this,tableLayout, textView);
        informationGetter.execute();





    }
}
