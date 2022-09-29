package com.example.secondlaboop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle args=getIntent().getExtras();
        double start=args.getDouble("start");
        double percent=args.getDouble("percent");
        double profit=args.getDouble("profit");
        String type=args.getString("type");
        String currency=args.getString("currency");
        TextView textGetter=findViewById(R.id.contributionText);
        textGetter.setText(String.valueOf(start) + " " + currency);
        textGetter=findViewById(R.id.PercentText);
        textGetter.setText(String.valueOf(percent) + "%");
        textGetter=findViewById(R.id.TypeText);
        textGetter.setText(String.valueOf(type));
        textGetter=findViewById(R.id.profitText);
        textGetter.setText(String.valueOf(profit) + " " + currency);
        textGetter=findViewById(R.id.totalText);
        textGetter.setText(String.valueOf(start+profit) + " " + currency);
    }
    public void returnOnClick(View view){
        Intent mainActivity=new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}