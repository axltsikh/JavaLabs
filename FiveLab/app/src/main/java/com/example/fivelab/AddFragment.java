package com.example.fivelab;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.databinding.DataBindingUtil;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.fivelab.databinding.FragmentAddBinding;
import java.io.File;
import java.sql.Date;
import java.util.Calendar;

public class AddFragment extends Fragment {

    private static final String TAG = "ExceptionLog";

    public AddFragment() {
    }
    DataBaseHelper helper;
    SQLiteDatabase db;
    Task task=new Task();
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAddBinding binding=DataBindingUtil.inflate(inflater,R.layout.fragment_add,
                container,false);
        rootView=binding.getRoot();
        binding.setTask(task);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(rootView.getContext(),R.array.ComplexityArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner ComplexitySpinner=rootView.findViewById(R.id.ComplexitySpinner);
        ComplexitySpinner.setAdapter(adapter);
        adapter=ArrayAdapter.createFromResource(rootView.getContext(),R.array.UrgencyArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ComplexitySpinner=rootView.findViewById(R.id.UrgencySpinner);
        ComplexitySpinner.setAdapter(adapter);
        CalendarView datePicker=rootView.findViewById(R.id.Deadline);
        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar c = Calendar.getInstance();
                c.set(i, i1, i2);
                task.Deadline = c.getTimeInMillis(); //this is what you want to use later
            }
        });
        try {
            helper = new DataBaseHelper(getContext());
            db = helper.open();
        }
        catch(Exception e){
            Log.d(TAG, "onCreateView: " + e.getMessage());
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        Button imageButton=rootView.findViewById(R.id.PhotoButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(image,1);
            }
        });
        Button saveButton=rootView.findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               if(task.Photo==null){
                   Toast toast= Toast.makeText(getContext(),"Выберите картинку!",Toast.LENGTH_SHORT);
                   toast.show();
                   return;
               }
               if(task.Name.equals("") || task.Description.equals("")){
                   Toast toast=Toast.makeText(getContext(),"Заполните поля!",Toast.LENGTH_SHORT);
                   toast.show();
                   return;
               }
               Log.d("ExceptionLog", "Filler: " + String.valueOf(task.Deadline));

               ContentValues cv=new ContentValues();
               cv.put(helper.ColumnName,task.Name);
               cv.put(helper.ColumnDescription,task.Description);
               cv.put(helper.ColumnComplexity,task.ComplexityPosition);
               cv.put(helper.ColumnUrgency,task.UrgencyPosition);
               cv.put(helper.ColumnDeadline,task.Deadline);
               cv.put(helper.ColumnPhoto,task.Photo);
               CheckBox box=rootView.findViewById(R.id.Favorite);
               boolean a=false;
               if(box.isChecked()){
                    a=true;
               }
               cv.put(helper.ColumnFavorite,a);
               db.insert(helper.TABLE,null,cv);
           }
        });

    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imagepath = cursor.getString(columnIndex);
                    task.Photo=imagepath;
                    cursor.close();
                    break;
            }
        }
    }
}