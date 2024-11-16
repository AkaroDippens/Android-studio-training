package com.example.myapplication1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

// Pattern pattern = Patten.compile(:*(.*)@(\\$*)$");

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Авторизация");
    }
    @SuppressLint("SetTextI18n")
    public void onSignClick(View view) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Pattern pattern2 = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$%^&*?])(?=.{7,})\\S+$");

        EditText editLogin = findViewById(R.id.editLogin);
        EditText editPassword = findViewById(R.id.editPassword);
        TextView textIn = findViewById(R.id.textView2);

        String editLog = editLogin.getText().toString();
        String editPas = editPassword.getText().toString();

        if (editLog.isEmpty() || editPas.isEmpty()) {
            textIn.setGravity(Gravity.CENTER);
            textIn.setText("Entering an email and password is required!");
        }else {
            if (!pattern.matcher(editLog).matches() || !pattern2.matcher(editPas).matches()) {
                textIn.setGravity(Gravity.CENTER);
                textIn.setText("Incorrect email address or password");
            }else {
                textIn.setGravity(Gravity.CENTER);
                textIn.setText("Successfully!");
                Intent i = new Intent(this, SecondActivity.class);
                i.putExtra("log", editLog);
                startActivity(i);
            }
        }
    }
}