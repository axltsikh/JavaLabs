package com.example.thirdlaboop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Date;
import java.util.ArrayList;

public class SecondPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        SpinnerFiller();
    }

    private void SpinnerFiller() {
        String[] platforms={"PC","PS4","XBOX ONE","XBOX SERIES X","XBOX SERIES S"};
        String[] genres={"Action","RPG","Quest","RTS","Sport","Racing","Fighting"};
        Spinner spin=findViewById(R.id.PlatformsSpinner);
        ArrayAdapter<String>adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,platforms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin=findViewById(R.id.Genre);
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,genres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    public void NextOnClick(View view){
        Intent nt=new Intent(this, ThirdPage.class);
        Game gObject=(Game)getIntent().getExtras().getSerializable("Game");
        ArrayList<Game> games=(ArrayList<Game>)getIntent().getExtras().getSerializable(("Games"));
        gObject=ObjectFiller();
        nt.putExtra("Game",gObject);
        nt.putExtra("Games",games);
        if(Wrapper.thirdPage){
            Wrapper.thirdPage=false;
            nt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(nt);
    }
    public void PreviousOnClick(View view){
        Intent nt=new Intent(this,MainActivity.class);
        nt.putExtras(getIntent().getExtras());
        startActivity(nt);
    }

    public Game ObjectFiller(){
        Bundle extras=getIntent().getExtras();
        Game gObject=(Game)extras.getSerializable("Game");
        DatePicker datePicker=findViewById(R.id.Date);
        gObject.Release=new Date(datePicker.getYear()-1900,datePicker.getMonth(),datePicker.getDayOfMonth());
        Spinner spinnerGetter=findViewById(R.id.Genre);
        gObject.Genre=spinnerGetter.getSelectedItem().toString();
        gObject.GenrePos=spinnerGetter.getSelectedItemPosition();
        spinnerGetter=findViewById(R.id.PlatformsSpinner);
        gObject.Platform=spinnerGetter.getSelectedItem().toString();
        gObject.PlatformPos=spinnerGetter.getSelectedItemPosition();
        return gObject;
    }

}