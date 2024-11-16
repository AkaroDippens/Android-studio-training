package com.example.myapplication1;

import static androidx.core.view.ViewCompat.setBackground;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setTitle("Настройки");
    }

    public void Checked(View view) {
        CheckBox checkBox = findViewById(R.id.checkBox);
        TextView textView = findViewById(R.id.textView4);
        if (checkBox.isChecked()) {
            textView.setText("Ну это настройки тип");
        }else{
            textView.setText("Настройки");
        }
    }
}