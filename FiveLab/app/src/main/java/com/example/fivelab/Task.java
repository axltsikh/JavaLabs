package com.example.fivelab;

import android.database.Cursor;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Task implements Serializable {
    public int ID;
    public String Name;
    public String Description;
    public int ComplexityPosition;
    public int UrgencyPosition;
    public long Deadline;
    public String Photo;
    public boolean Favorite;
    public Task(){}
    public Task(int _id,String _name,String _description,int _complexity,int _urgency,long _deadline,String _photo,boolean _favorite){
        ID=_id;
        Name=_name;
        Description=_description;
        ComplexityPosition=_complexity;
        UrgencyPosition=_urgency;
        Deadline=_deadline;
        Photo=_photo;
        Favorite=_favorite;
    }
    static ArrayList<Task> tasks=new ArrayList<>();
    static Cursor cursor;
    public static void Serialize(File f){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            Gson gson = new GsonBuilder().create();
            bw.write(gson.toJson(tasks));
            bw.close();
        }
        catch(Exception e){
            Log.d("ExceptionLog","Serialize: " + e.getMessage());
        }
    }
    public static void Deserialize(File f){
        if(f.exists()){
            try{
                if(f.length()!=0){
                    BufferedReader br=new BufferedReader(new FileReader(f));
                    Gson gson=new GsonBuilder().create();
                    tasks=gson.fromJson(br.readLine(),new TypeToken<ArrayList<Task>>(){}.getType());
                }
            }
            catch(Exception e){
                Log.d("ExceptionLog", "Deserialize: " + e.getMessage());
            }
        }
        else{
            try {
                f.createNewFile();
            }
            catch(Exception e){
                Log.d("ExceptionLog", "Deserialize: " + e.getMessage());
            }
        }
    }
}
