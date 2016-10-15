package com.fuagra.myamazingcatcounter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "catCounter";
    private TextView resultTextView;
    private Button dimaButton;
    private Button nastyaButton;
    private final String keyDima = "Dima";
    private final String keyNastya = "Nastya";
    private DatabaseReference firebaseScore;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseScore = database.getReference("score");

        firebaseScore.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

                score = Integer.parseInt(value);
                if (score > 0) {
                    resultTextView.setText("+" + score + " Dima");
                } else if (score < 0) {
                    resultTextView.setText("+" + (score * -1) + " Nastya");
                } else {
                    resultTextView.setText("Draw");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @SuppressLint("CommitPrefEdits")
    private void incrementCount(String key) {
        if (key.equals(keyDima)) {
            firebaseScore.setValue((score + 1) + "");
        }  else{
            firebaseScore.setValue((score - 1) + "");
        }
    }
}
