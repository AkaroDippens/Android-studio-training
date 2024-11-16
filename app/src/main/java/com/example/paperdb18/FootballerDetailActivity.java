package com.example.paperdb18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FootballerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footballer_detail);

        TextView footballer_name = findViewById(R.id.editNameText);
        TextView football_age = findViewById(R.id.editAgeText);
        Button editButton = findViewById(R.id.editButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        PaperDBHelper paperDBHelper = new PaperDBHelper();
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            int footballerId = extras.getInt("id");
            Footballer footballer = paperDBHelper.getFootballerById(footballerId);
            if (footballer != null) {
                footballer_name.setText(footballer.getName());
                football_age.setText(footballer.getAge());
            }

            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    paperDBHelper.deleteFootballer(footballerId);
                    Intent intent = new Intent(FootballerDetailActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String updatedName = footballer_name.getText().toString();
                    String updatedAge = football_age.getText().toString();

                    if (!updatedName.isEmpty() && !updatedAge.isEmpty() &&
                            updatedAge.matches("^[0-9]+$")) {
                        footballer.setName(updatedName);
                        footballer.setAge(updatedAge);

                        paperDBHelper.updateFootballer(footballer);

                        Intent intent = new Intent(FootballerDetailActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(), "Не удалось изменить данные", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}