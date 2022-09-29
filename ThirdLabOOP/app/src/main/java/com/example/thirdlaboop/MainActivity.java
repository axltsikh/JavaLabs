package com.example.thirdlaboop;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Game GameObject;
    final String fname="Lab.json";
    ArrayList<Game> games;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameObject=new Game();
        collectionGetter();
    }
    public void NextButtonOnClick(View view){
        if(!Validator()) {
            return;
        }
        Intent nt = new Intent(this, SecondPage.class);
        EditText textGetter = findViewById(R.id.GameName);
        GameObject.GameName = textGetter.getText().toString();
        textGetter = findViewById(R.id.Developer);
        GameObject.Developer = textGetter.getText().toString();
        textGetter = findViewById(R.id.Publisher);
        GameObject.Publisher = textGetter.getText().toString();
        nt.putExtra("Games", games);
        nt.putExtra("Game", GameObject);
        if(Wrapper.secondPage){
            Wrapper.secondPage=false;
            nt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(nt);
    }
    public boolean Validator(){
        EditText text=findViewById(R.id.GameName);
        TextInputLayout textLayout=findViewById(R.id.GameNameLayout);
        if(text.getText().toString().equals("")){
            textLayout.setError(getString(R.string.Error));
            return false;
        }
        else{
            textLayout.setErrorEnabled(false);
        }
        text=findViewById(R.id.Developer);
        textLayout=findViewById(R.id.DeveloperLayout);
        if(text.getText().toString().equals("")){
            textLayout.setError(getString(R.string.Error));
            return false;
        }
        else{
            textLayout.setErrorEnabled(false);
        }
        text=findViewById(R.id.Publisher);
        textLayout=findViewById(R.id.PublisherLayout);
        if(text.getText().toString().equals("")){
            textLayout.setError(getString(R.string.Error));
            return false;
        }
        else{
            textLayout.setErrorEnabled(false);
        }
        return true;
    }

    public void collectionGetter(){
        GsonBuilder gsonBuilder=new GsonBuilder();
        Gson gs=gsonBuilder.create();
        try{
            File f=new File(super.getFilesDir(),fname);
            BufferedReader bw=new BufferedReader(new FileReader(f));
            if(f.length()!=0) {
                games = gs.fromJson(bw.readLine(), ArrayList.class);
            }
            else{
                games=new ArrayList<>();
            }
        }
        catch(IOException e){
            Log.d("ExceptionLog",e.getMessage());
        }
    }

    public void cleaner(){
        EditText text=findViewById(R.id.GameName);
        text.setText("");
    }

}