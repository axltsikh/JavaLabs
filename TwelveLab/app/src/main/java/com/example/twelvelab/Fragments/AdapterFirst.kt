package com.example.twelvelab.Fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twelvelab.NewsClasses.Item
import com.example.twelvelab.R
import java.util.regex.Pattern

class AdapterFirst(private val items: List<Item>,private val onItemCLicked:(position:Int)->Unit) :
    RecyclerView.Adapter<AdapterFirst.MyViewHolder>() {

    class MyViewHolder(itemView: View,private val onItemCLicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val firstTextView: TextView = itemView.findViewById(R.id.firstItemFirstTextView)
        val secondTextView: TextView = itemView.findViewById(R.id.secondItemSecondTextView)
        val thirdTextView: TextView = itemView.findViewById(R.id.thirdItemThirdTextView)
        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(v:View){
            val position=adapterPosition
            onItemCLicked(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context=parent.context
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.first_item, parent, false)
        return MyViewHolder(itemView,onItemCLicked)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.firstTextView.text=items[position].title
        val delim="\\</strong>"
        val arr= Pattern.compile(delim).split(items[position].description)
        var Buffer=arr[2]
        var result:String=""
        var i=0
        while(Buffer[i]!='<'){
            result+=Buffer[i]
            i++
        }
        holder.secondTextView.text="Начало события:" + result
        i=0
        Buffer=arr[3]
        result=""
        while(Buffer[i]!='<'){
            result+=Buffer[i]
            i++
        }
        holder.thirdTextView.text="Конец события: " + result
    }

    override fun getItemCount(): Int {
        return items.size
    }
}