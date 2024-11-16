package com.example.sqliteproject;

public class Footballer {
    private int ID_Footballer;
    private String Footballer_first_name;
    private String Footballer_last_name;
    private String Football_club;

    public Footballer(int ID_Footballer, String footballer_first_name, String footballer_last_name, String football_club) {
        this.ID_Footballer = ID_Footballer;
        this.Footballer_first_name = footballer_first_name;
        this.Footballer_last_name = footballer_last_name;
        this.Football_club = football_club;
    }

    public int getID_Footballer(){
        return ID_Footballer;
    }

    public void setID_Footballer(int ID_Footballer){
        this.ID_Footballer = ID_Footballer;
    }

    public String getFootballer_first_name(){
        return Footballer_first_name;
    }

    public void setFootballer_first_name(String Footballer_first_name){
        this.Footballer_first_name = Footballer_first_name;
    }

    public String getFootballer_last_name(){
        return Footballer_last_name;
    }

    public void setFootballer_last_name(String Footballer_last_name){
        this.Footballer_last_name = Footballer_last_name;
    }

    public String getFootball_club(){
        return Football_club;
    }

    public void setFootball_club(String Football_club){
        this.Football_club = Football_club;
    }


}
