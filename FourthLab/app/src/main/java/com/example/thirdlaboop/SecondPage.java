package com.example.thirdlaboop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class SecondPage extends AppCompatActivity {

    static final String fname="Lab.json";
    static final int GALLERY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
    }

    public void NextOnClick(View view){
        Intent nt=new Intent(this, FinalPage.class);
        Game gObject=(Game)getIntent().getExtras().getSerializable("game");
        ArrayList<Game> games=(ArrayList<Game>)getIntent().getExtras().getSerializable(("games"));
        EditText text=findViewById(R.id.SocialMedia);
        gObject.SocialMedia=text.getText().toString();
        nt.putExtra("games",games);
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        try {
            games.add(gObject);
            String jsString = gson.toJson(games);
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File(super.getFilesDir(),fname),false));
            bw.write(jsString);
            bw.close();
        }
        catch(Exception e){
            Log.d("ExceptLog",e.getMessage());
        }
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

    public void PhotoButtonOnClick(View view){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Bitmap bitmap = null;

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        }
    }


}