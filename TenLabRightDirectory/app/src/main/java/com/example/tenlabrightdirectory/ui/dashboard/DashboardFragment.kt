package com.example.tenlabrightdirectory.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tenlabrightdirectory.R
import com.example.tenlabrightdirectory.databinding.FragmentDashboardBinding
enum class Holiday(val value:String){
    NewYear("010123"),EightsOfMarch("080323"),LaborDay("010623")
}
class DashboardFragment : Fragment() {
    lateinit var rootView:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView=inflater.inflate(R.layout.fragment_dashboard,container,false);
        var but=rootView.findViewById<Button>(R.id.dateButton);
        but.setOnClickListener(View.OnClickListener {
            DateButtonOnClick()
        })
        but=rootView.findViewById<Button>(R.id.operationButton);
        but.setOnClickListener(View.OnClickListener {
            OperationButtonOnClick()
        })
        but=rootView.findViewById<Button>(R.id.StringButton);
        but.setOnClickListener(View.OnClickListener {
            StringButtonOnClick()
        })
        but=rootView.findViewById(R.id.indexButton)
        but.setOnClickListener(View.OnClickListener {
            IndexButtonClick()
        })
        return rootView;
    }
    fun DateButtonOnClick(){
        val textv=rootView.findViewById<TextView>(R.id.dateTextView)
        val date=rootView.findViewById<EditText>(R.id.date)
        val text=rootView.findViewById<TextView>(R.id.dateTextView)
        if(date.text.toString().isNullOrEmpty()){
            textv.text="Строка не может быть пустой"
        }
        else{
            when(date.text.toString()){
                Holiday.EightsOfMarch.value->textv.text="Этот день праздничный"
                Holiday.LaborDay.value->textv.text="Этот день праздничный"
                Holiday.NewYear.value->textv.text="Этот день праздничный"
                else -> textv.text="Этот день будний";
            }
        }
    }
    fun OperationButtonOnClick(){
        val first=rootView.findViewById<EditText>(R.id.firstValue)
        val second=rootView.findViewById<EditText>(R.id.secondtValue)
        val operation=rootView.findViewById<EditText>(R.id.operationValue)
        val text=rootView.findViewById<TextView>(R.id.operationTextView)
        val operationchar:Char= operation.text.toString().toCharArray()[0]
        text.setText(doOperation(first.text.toString().toInt(),second.text.toString().toInt(),operationchar).toString());
    }
    fun StringButtonOnClick(){
        val str:String=rootView.findViewById<EditText>(R.id.FirstString).text.toString()
        val pattern=rootView.findViewById<EditText>(R.id.PatternString).text.toString()
        val text=rootView.findViewById<TextView>(R.id.stringTextView)
        text.text=(str.Coincidence(pattern)).toString()

    }
    fun IndexButtonClick(){
        var text:TextView=rootView.findViewById(R.id.indexTextView)
        var buffer:IntArray=intArrayOf(5,7,1,4,15,15,8,2,72,9,71,26,55,99)
        text.setText("Начальный массив: ")
        for(a in buffer){
            text.append("$a,")
        }
        text.setText("\n")
        text.append("Максимальный элемент: " + buffer.indexOFMax())
    }

    fun IntArray.indexOFMax():Int?{
        if(this==null)
            return null;
        var maxValue=this[0];
        var values:Int=0;
        for(it in this) {
            if(it==maxValue){
                values++;
            }
            if(it>maxValue){
                maxValue=it;
                values=1;
            }
        }
        if(values!=1){
            return null;
        }
        return maxValue;
    }
    fun doOperation(a:Int,b:Int,operation:Char):Double{
        when(operation){
            '+' -> return (a+b).toDouble()
            '-' -> return (a-b).toDouble()
            '*' -> return (a*b).toDouble()
            '/' -> return (a/b).toDouble()
            '%' -> return (a%b).toDouble()
            else -> throw Exception("Неверная операция")
        }

    }
    fun String.Coincidence(pattern:String):Int{
        var i=0
        var counter=0
        if(this.length>pattern.length){
            pattern.forEach {
                if(it==this[i])
                    counter++
                i++
            }
            return counter
        }
        else{
            this.forEach {
                if(it==pattern[i])
                    counter++
                i++
            }
            return counter
        }
    }
}