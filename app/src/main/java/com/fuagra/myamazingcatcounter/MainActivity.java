package com.fuagra.myamazingcatcounter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Button dimaButton;
    private Button nastyaButton;
    private final String keyDima = "Dima";
    private final String keyNastya = "Nastya";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("general", Context.MODE_PRIVATE);
        resultTextView = (TextView) findViewById(R.id.result_textview);
        dimaButton = (Button) findViewById(R.id.dima_button);
        nastyaButton = (Button) findViewById(R.id.nastya_button);


        dimaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(keyDima);
            }
        });

        nastyaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementCount(keyNastya);
            }
        });

        printResult();

    }

    @SuppressLint("CommitPrefEdits")
    private void incrementCount(String key) {
        int count = prefs.getInt(key, 0) + 1;
        prefs.edit().putInt(key, count).commit();
        printResult();

    }

    private void printResult() {
        int dimaCount = prefs.getInt(keyDima, 0);
        int nastyaCount = prefs.getInt(keyNastya, 0);

        if (dimaCount > nastyaCount) {
            resultTextView.setText("+" + (dimaCount - nastyaCount) + " Dima");
        } else if (nastyaCount > dimaCount) {
            resultTextView.setText("+" + (nastyaCount - dimaCount) + " Nastya");
        } else {
            resultTextView.setText("Draw");
        }
    }
}
