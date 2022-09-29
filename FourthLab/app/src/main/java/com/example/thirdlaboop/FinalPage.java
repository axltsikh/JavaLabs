package com.example.thirdlaboop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FinalPage extends AppCompatActivity {

    final String fname="Lab.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);
    }



    public void FinishOnClick(View view){
        GsonBuilder builder=new GsonBuilder();
        Gson gs=builder.create();
        try {
            Game gObject=(Game)getIntent().getExtras().getSerializable("game");
            ArrayList<Game> games=(ArrayList<Game>)getIntent().getExtras().getSerializable("games");
            games.add(gObject);
            String jsString = gs.toJson(games);
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File(super.getFilesDir(),fname),false));
            bw.write(jsString);
            bw.close();
        }
        catch(Exception e){
            Log.d("ExceptionLog",e.getMessage());
        }
        Toast toast=Toast.makeText(this,"Успешно создано и записано в файл",Toast.LENGTH_LONG);
        toast.show();
        Wrapper.setAllTrue();
        Intent toBegin=new Intent(this,MainActivity.class);
        toBegin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(toBegin);
    }
    public void PreviousOnClick(View view) {
        Intent nt = new Intent(this, SecondPage.class);
        nt.putExtras(getIntent().getExtras());
        startActivity(nt);
    }

}