package com.example.fivelab;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fivelab.databinding.FragmentTaskBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Date;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskFragment extends Fragment {

    public TaskFragment() {
        // Required empty public constructor
    }
    int ID;
    Spinner ComplexitySpinner;
    Spinner UrgencySpinner;
    EditText Name;
    EditText Description;
    String photoBuffer;
    long time;
    CheckBox box;
    int favoritevalue;
    DataBaseHelper helper;
    SQLiteDatabase db;
    View rootView;

    Task task;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_task, container, false);
        helper=new DataBaseHelper(getContext());
        ID=getArguments().getInt("ID");
        try {
            db = helper.open();
        }
        catch(Exception e){
            Log.d("ExceptionLog", "onCreateView: " + e.getMessage());
        }
        task=Task.tasks.get(ID);
        Filler();
        setHasOptionsMenu(true);
        return rootView;
    }
    public void Filler(){

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(rootView.getContext(),R.array.ComplexityArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ComplexitySpinner=rootView.findViewById(R.id.ComplexitySpinnerDetails);
        ComplexitySpinner.setAdapter(adapter);
        ComplexitySpinner.setSelection(task.ComplexityPosition);

        adapter=ArrayAdapter.createFromResource(rootView.getContext(),R.array.UrgencyArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UrgencySpinner=rootView.findViewById(R.id.UrgencySpinnerDetails);
        UrgencySpinner.setAdapter(adapter);
        UrgencySpinner.setSelection(task.UrgencyPosition);

        Name=rootView.findViewById(R.id.NameDetails);
        Name.setText(String.valueOf(task.Name));
        Description=rootView.findViewById(R.id.DescriptionDetails);
        Description.setText(task.Description);

        photoBuffer=task.Photo;
        ImageView image=rootView.findViewById(R.id.PhotoTask);
        image.setImageBitmap(BitmapFactory.decodeFile(photoBuffer));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent image=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(image,1);
            }
        });
        box=rootView.findViewById(R.id.FavoriteDetail);
        if(task.Favorite){
            box.setChecked(true);
        }
        else{
            box.setChecked(false);
        }

        Button button=rootView.findViewById(R.id.ChangeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();
                cv.put(helper.ColumnName,Name.getText().toString());
                cv.put(helper.ColumnDescription,Description.getText().toString());
                cv.put(helper.ColumnComplexity,ComplexitySpinner.getSelectedItemPosition());
                cv.put(helper.ColumnUrgency,UrgencySpinner.getSelectedItemPosition());
                cv.put(helper.ColumnDeadline,time);
                cv.put(helper.ColumnPhoto,photoBuffer);
                cv.put(helper.ColumnFavorite,true);
                boolean a=false;
                if(box.isChecked()){
                    a=true;
                }
                cv.put(helper.ColumnFavorite,a);
                db.update(helper.TABLE,cv,helper.ColumnID+ "=" + task.ID,null);
            }
        });
        CalendarView date= rootView.findViewById(R.id.DeadLineTask);
        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar c = Calendar.getInstance();
                c.set(i, i1, i2);
                time = c.getTimeInMillis(); //this is what you want to use later
            }
        });
        date.setDate(task.Deadline);
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
                    photoBuffer = cursor.getString(columnIndex);
                    ImageView image=rootView.findViewById(R.id.PhotoTask);
                    image.setImageBitmap(BitmapFactory.decodeFile(photoBuffer));
                    cursor.close();
                    break;
            }
        }
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return;
        getActivity().getMenuInflater().inflate(R.menu.actionbarmenu,menu);
        MenuItem item=menu.findItem(R.id.Find);
        menu.removeItem(R.id.Find);
        item=menu.findItem(R.id.Up);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(getActivity().getSupportFragmentManager().getBackStackEntryCount()>0){
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else{

                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu,menuInflater);
    }
}