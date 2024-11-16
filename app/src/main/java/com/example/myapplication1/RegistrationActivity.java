package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("Login", editLoginReg.getText());
//            startActivity(intent);
        }

    @SuppressLint("SetTextI18n")
    public void ButtonClick(View view) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Pattern pattern2 = Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$%^&*?])(?=.{7,})\\S+$");

        EditText editLoginReg = findViewById(R.id.editLoginReg);
        EditText editPassword = findViewById(R.id.editPasswordReg);
        TextView textIn = findViewById(R.id.textView6);

        String editLogReg = editLoginReg.getText().toString();
        String editPasReg = editPassword.getText().toString();

        if (editLogReg.isEmpty() || editPasReg.isEmpty()) {
            textIn.setGravity(Gravity.CENTER);
            textIn.setText("Entering an email and password is required!");
        }else {
            if (!pattern.matcher(editLogReg).matches() || !pattern2.matcher(editPasReg).matches()) {
                textIn.setGravity(Gravity.CENTER);
                textIn.setText("Incorrect email address or password");
            }else {
                textIn.setGravity(Gravity.CENTER);
                textIn.setText("Successfully!");
                Intent i = new Intent(this, SecondActivity.class);
                startActivity(i);
            }
        }
    }
}
