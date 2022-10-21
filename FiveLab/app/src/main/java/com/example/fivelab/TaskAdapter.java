package com.example.fivelab;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    interface OnTaskClickListener{
        void onTaskClick(Task task,int position);
    }
    private final OnTaskClickListener onClickListener;
    private LayoutInflater inflater;
    private List<Task> tasks;
    public TaskAdapter(Context context,  List<Task> gettedtasks,OnTaskClickListener onClickListener){
        this.onClickListener=onClickListener;
        tasks=gettedtasks;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=inflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount(){
        return tasks.size();
    }
    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder,int position){
        Task task=tasks.get(position);
        Log.d("ExceptionLog", "onBindViewHolder: " + tasks.size());
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(task.Photo));
        holder.taskName.setText(task.Name);
        holder.taskDescription.setText(task.Description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onTaskClick(task,position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        final ImageView imageView;
        final TextView taskName,taskDescription;
        ViewHolder  (View view){
            super(view);
            imageView=view.findViewById(R.id.CustomItemImage);
            taskName=view.findViewById(R.id.CustomItemName);
            taskDescription=view.findViewById(R.id.CustomItemDescription);
            view.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle("Select The Action");
            menu.add(0, v.getId(), 0, "Удалить");//groupId, itemId, order, title

        }
    }
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }
}
