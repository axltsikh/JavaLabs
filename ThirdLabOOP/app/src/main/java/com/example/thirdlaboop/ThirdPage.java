package com.example.thirdlaboop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ThirdPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_page);
        SpinnerFiller();
//        ValuePutter();
    }

    private void SpinnerFiller() {
        String[] ages={"0+","6+","12+","16+","18+"};
        Spinner spin=findViewById(R.id.AgeSpinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,ages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    public void NextOnClick(View view){
        if(!Validator()){
            return;
        }
        Intent nt=new Intent(this,FinalPage.class);
        Game gObject=(Game)getIntent().getExtras().getSerializable("Game");
        ArrayList<Game> games=(ArrayList<Game>)getIntent().getExtras().getSerializable(("Games"));
        gObject=ObjectFiller();
        nt.putExtra("Game",gObject);
        nt.putExtra("Games",games);
        startActivity(nt);
    }
    public void PreviousOnClick(View view){
        Intent nt=new Intent(this,SecondPage.class);
        nt.putExtras(getIntent().getExtras());

        startActivity(nt);
    }

    public Game ObjectFiller(){
        Bundle extras=getIntent().getExtras();
        Game gObject=(Game)extras.getSerializable("Game");
        Spinner spinner=findViewById(R.id.AgeSpinner);
        gObject.PEGI=spinner.getSelectedItem().toString();
        gObject.PEGIPos=spinner.getSelectedItemPosition();
        EditText textGetter=findViewById(R.id.Version);
        gObject.Version=textGetter.getText().toString();
        return gObject;
    }
    public boolean Validator(){
        EditText text=findViewById(R.id.Version);
        TextInputLayout textLayout=findViewById(R.id.VersionLayout);
        if(text.getText().toString().equals("")){
            textLayout.setError(getString(R.string.Error));
            return false;
        }
        else{
            textLayout.setErrorEnabled(false);
        }
        return true;
    }
}