package com.example.fivelab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

public class ListFragment extends Fragment {

    private static final String TAG = "ExceptionLog";
    DataBaseHelper helper;
    public ListFragment() {
        // Required empty public constructor
    }
    View rootView;
    RecyclerView list;
    SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        helper=new DataBaseHelper(getContext());
        helper.create_db();
        Filler();
        setHasOptionsMenu(true);
        return rootView;
    }
    public void Filler(){
        list = rootView.findViewById(R.id.TaskList);
        try {
            db = helper.open();
        }
        catch(Exception e){
            Log.d("ExceptionLog", "ListFragment: Filler: " + e.getMessage());
        }
            Task.cursor=db.rawQuery("select * from " + DataBaseHelper.TABLE,null);
        Task.tasks.clear();
            while(Task.cursor.moveToNext()){
                int checkedvalue=Task.cursor.getInt(7);
                boolean checkedBool=false;
                if(checkedvalue==1){
                    checkedBool=true;
                }
                else{
                    checkedBool=false;
                }
                Task buffer=new Task(Task.cursor.getInt(0),Task.cursor.getString(1),
                        Task.cursor.getString(2),Task.cursor.getInt(3),
                        Task.cursor.getInt(4),Task.cursor.getLong(5),
                        Task.cursor.getString(6),checkedBool);
                Task.tasks.add(buffer);
            }
            TaskAdapter.OnTaskClickListener taskClickListener=new TaskAdapter.OnTaskClickListener() {
                @Override
                public void onTaskClick(Task task, int position) {
                    Fragment fr = new TaskFragment();
                    Bundle args = new Bundle();
                    args.putInt("ID", position);
                    fr.setArguments(args);
                    if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.TaskContainer, fr).commit();
                    else
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ListContainer, fr).addToBackStack(null).commit();
                }
            };
            TaskAdapter adapter=new TaskAdapter(rootView.getContext(),Task.tasks,taskClickListener);
            list.setAdapter(adapter);
            registerForContextMenu(list);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        int position = -1;
        try {
            position = ((TaskAdapter) list.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        db.delete(helper.TABLE,"_id = " + Task.tasks.get(position).ID,null);
        getActivity().recreate();
        return true;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater) {
        getActivity().getMenuInflater().inflate(R.menu.actionbarmenu,menu);
        MenuItem item=menu.findItem(R.id.Find);
        SearchView search=(SearchView) MenuItemCompat.getActionView(item);
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                Log.d("ExceptionLog", "onQueryTextChange: " + "sad");
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String s) {
//                Task.cursor=db.rawQuery("select * from " + helper.TABLE + " where " +
//                        helper.ColumnName + " like ?", new String[]{"%" + s + "%"});
//                String[] headers = new String[]{DataBaseHelper.ColumnName, DataBaseHelper.ColumnDescription};
//                userAdapter = new SimpleCursorAdapter(getContext(), android.R.layout.two_line_list_item,
//                        Task.cursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
//                list.setAdapter(userAdapter);
//                Log.d("ExceptionLog", "onQueryTextChange: " + "sad");
//                return false;
//            }
//        });
        item=menu.findItem(R.id.Up);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(getActivity().getSupportFragmentManager().getBackStackEntryCount()>0){
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else{
                    return false;
                }
                return true;
            }
        });

        item=menu.findItem(R.id.Favorite);
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Task.cursor=db.rawQuery("select * from " + helper.TABLE + " where " +
//                        helper.ColumnFavorite + "=?", new String[]{String.valueOf(1)});
//                String[] headers = new String[]{DataBaseHelper.ColumnName, DataBaseHelper.ColumnDescription};
//                SimpleCursorAdapter userAdapter = new SimpleCursorAdapter(getContext(), android.R.layout.two_line_list_item,
//                        Task.cursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
//                list.setAdapter(userAdapter);
//                return true;
//            }
//        });
        super.onCreateOptionsMenu(menu,menuInflater);
    }
}