package com.example.sqliteproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FootballersDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footballer_detail);

        TextView footballer_first_name = findViewById(R.id.detail_first_name);
        TextView footballer_last_name = findViewById(R.id.detail_last_name);
        TextView football_club = findViewById(R.id.detail_club);
        Button editButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int footballerId = extras.getInt("id");
            Footballer footballer = dataBaseHelper.getFootballerById(footballerId);
            if (footballer != null) {
                footballer_first_name.setText(footballer.getFootballer_first_name());
                footballer_last_name.setText(footballer.getFootballer_last_name());
                football_club.setText(footballer.getFootball_club());
            }

            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    deleteFootballerFromDatabase(footballerId);
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String updatedFirstName = footballer_first_name.getText().toString();
                    String updatedLastName = footballer_last_name.getText().toString();
                    String updatedClub = football_club.getText().toString();

                    footballer.setFootballer_first_name(updatedFirstName);
                    footballer.setFootballer_last_name(updatedLastName);
                    footballer.setFootball_club(updatedClub);

                    dataBaseHelper.updateFootballer(footballer);

                    Intent intent = new Intent(FootballersDetailActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void deleteFootballerFromDatabase(int id) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        dbHelper.deleteFootballer(id);

        Intent intent = new Intent(FootballersDetailActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}