package com.example.secondlaboop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpinnerFiller();
    }

    private void SpinnerFiller() {
        String[] years={"1 год","2 года","3 года","4 года","5 лет"};
        String[] currency={"BYN","RUB","USD"};
        String[] periods={"Один раз в год","Два раза в год","Три раза в год"};
        Spinner spinner=findViewById(R.id.DurationSpinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner=findViewById((R.id.currencySpinner));
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,currency);
        adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        spinner.setAdapter(adapter);
        spinner=findViewById((R.id.PeriodSpinner));
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,periods);
        adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        spinner.setAdapter(adapter);
    }

    public void CalculateOnClick(View view){

        EditText editBuffer=(EditText)findViewById(R.id.Amount);
        double money=Double.parseDouble(editBuffer.getText().toString());
        editBuffer=(EditText)findViewById((R.id.textView8));
        double Percent=Double.parseDouble(editBuffer.getText().toString());
        Spinner spinnerGet=(Spinner)findViewById((R.id.DurationSpinner));
        int termSpinner=spinnerGet.getSelectedItemPosition()+1;
        spinnerGet=(Spinner)findViewById(R.id.PeriodSpinner);
        int periodSpinner=spinnerGet.getSelectedItemPosition()+1;
        RadioButton typeButton=findViewById(R.id.payButton);
        double result=0;
        String percentType;
        if(typeButton.isChecked()){
            int counter=termSpinner*periodSpinner;
            for(int i=0;i<counter;i++){
                result+=money*(Percent/100);
            }
            percentType=getResources().getString(R.string.add);
        }
        else{
            int counter=termSpinner*periodSpinner;
            for(int i=0;i<counter;i++){
                result+=money*(Percent/100);
                money+=result;
            }
            percentType=getResources().getString(R.string.pay);
        }
        spinnerGet=findViewById(R.id.currencySpinner);
        String currency=spinnerGet.getSelectedItem().toString();
        Intent resultActivity=new Intent(this, ResultActivity.class);
        resultActivity.putExtra("start",money);
        resultActivity.putExtra("percent",Percent);
        resultActivity.putExtra("type",percentType);
        resultActivity.putExtra("profit",result);
        resultActivity.putExtra("currency",currency);
        Log.d("aaa",String.valueOf(money));
        startActivity(resultActivity);
    }


}