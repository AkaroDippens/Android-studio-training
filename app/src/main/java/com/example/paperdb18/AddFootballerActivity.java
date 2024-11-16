package com.example.paperdb18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.paperdb.Paper;

public class AddFootballerActivity extends AppCompatActivity {

    EditText footballerName, footballerAge;
    Button addFootballer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_footballer);

        Paper.init(this);

        footballerName = findViewById(R.id.footballer_name);
        footballerAge = findViewById(R.id.footballer_age);
        addFootballer = findViewById(R.id.button_add_footballer);

        PaperDBHelper paperDBHelper = new PaperDBHelper();

        addFootballer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!footballerName.getText().toString().isEmpty() && !footballerAge.getText().toString().isEmpty() &&
                    footballerAge.getText().toString().matches("^[0-9]+$")) {
                    Footballer footballer = new Footballer(0, footballerName.getText().toString(),
                            footballerAge.getText().toString());
                    paperDBHelper.addFootballer(footballer, getApplicationContext());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Не удалось добавить футболиста", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}