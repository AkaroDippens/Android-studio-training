package com.example.sqliteproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddFootballerActivity extends AppCompatActivity {
    EditText footballerFirstName, footballerLastName, footballClub;
    Button addFootballer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_footballer);

        footballerFirstName = findViewById(R.id.footballer_first_name);
        footballerLastName = findViewById(R.id.footballer_last_name);
        footballClub = findViewById(R.id.football_club);
        addFootballer = findViewById(R.id.button);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        addFootballer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Footballer footballer = new Footballer(0, footballerFirstName.getText().toString(),
                        footballerLastName.getText().toString(), footballClub.getText().toString());

                dataBaseHelper.addFootballer(footballer);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
