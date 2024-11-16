package com.example.paperdb18;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import io.paperdb.Paper;

public class PaperDBHelper {
    public ArrayList<Footballer> getFootballers(){
        return Paper.book("footballers").read("footballers", new ArrayList<Footballer>());
    }

    public void saveFootballerList(ArrayList<Footballer> footballers){
        Paper.book("footballers").write("footballers", footballers);
    }

    public void addFootballer(Footballer footballer, Context context){
        ArrayList<Footballer> footballers = getFootballers();
        for (int i = 0; i < footballers.size(); i++){
            if (footballers.get(i).getName().equals(footballer.getName())){
                Toast.makeText(context, "Такой футболист уже существует", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        footballers.add(footballer);
        saveFootballerList(footballers);
    }

    public Footballer getFootballerById(int id) {
        ArrayList<Footballer> footballers = getFootballers();
        for (Footballer footballer : footballers){
            if (footballer.getId() == id){
                return footballer;
            }
        }
        return null;
    }

    public void deleteFootballer(int id) {
        ArrayList<Footballer> footballers = getFootballers();
        for (int i = 0; i < footballers.size(); i++) {
            if (footballers.get(i).getId() == id) {
                footballers.remove(i);
            }
        }
        saveFootballerList(footballers);
    }

    public void updateFootballer(Footballer footballer) {
        ArrayList<Footballer> footballers = getFootballers();
        for (int i = 0; i < footballers.size(); i++) {
            if (footballers.get(i).getId() == footballer.getId()) {
                footballers.set(i, footballer);
                break;
            }
        }
        saveFootballerList(footballers);
    }
}
