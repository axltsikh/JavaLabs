package com.example.fivelab;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.util.List;

public class DialogDelete extends DialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Удаление")
                .setMessage("Вы уверены?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db;
                        DataBaseHelper helper=new DataBaseHelper(getActivity());
                        int position = getArguments().getInt("position");
                        Task buffer=Task.tasks.get(position);
                        try {
                            db = helper.open();
                            db.delete(DataBaseHelper.TABLE,"_id = ?", new String[]{String.valueOf(buffer.ID)});
                        }
                        catch(Exception e){
                            Log.d("ExceptionLog", "ListFragment: Filler: " + e.getMessage());
                        }
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ListContainer,new ListFragment()).commit();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }
}
