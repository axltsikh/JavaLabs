package com.example.fivelab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH; // полный путь к базе данных
    private static String DB_NAME = "Seven.db";
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "Task"; // название таблицы в бд
    public static String ColumnName="Name";
    public static String ColumnID="_id";
    public static String ColumnDescription="Description";
    public static String ColumnComplexity="Complexity";
    public static String ColumnUrgency="Urgency";
    public static String ColumnDeadline="Deadline";
    public static String ColumnPhoto="Photo";
    public static String ColumnFavorite="Favorite";
    private Context context;
    DataBaseHelper(Context context){
        super(context,DB_NAME,null,SCHEMA);
        this.context=context;
        DB_PATH=context.getFilesDir().getPath() + "/" + DB_NAME;
        Log.d("ExceptionLog", "DataBaseHelper: " + DB_PATH);
    }
    public void create_db(){

        File file = new File(DB_PATH);
        if (!file.exists()) {
            //получаем локальную бд как поток
            try(InputStream myInput = context.getAssets().open(DB_NAME);
                // Открываем пустую бд
                OutputStream myOutput = new FileOutputStream(DB_PATH)) {

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
