package com.example.tenlabrightdirectory.ui.notifications

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
import com.example.tenlabrightdirectory.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    lateinit var rootView:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView=inflater.inflate(R.layout.fragment_notifications,container,false);
        var but=rootView.findViewById<Button>(R.id.facButton);
        but.setOnClickListener(View.OnClickListener {
            factorial()
        })
        but=rootView.findViewById<Button>(R.id.simpleButton);
        but.setOnClickListener(View.OnClickListener {
            simple()
        })
        return rootView;
    }
    fun factorial(){
        val text=rootView.findViewById<TextView>(R.id.facText);
        val number:Int=rootView.findViewById<EditText>(R.id.factorial).text.toString().toInt()
        text.setText("Факториал(цикл): " + FactorialCycle(number) + "\n" +
            "Факториал(диапазон): " + FactorialDiapason(number) + "\n" +
                "Факториал(рекурсия): " + FactorialRecusrion(number))
    }
    fun simple(){
        val text=rootView.findViewById<TextView>(R.id.simpleText);
        val range=2..10000
        var counter=0;
        range.forEach(){
            if(isPrime(it)==true)
                counter++
        }
        text.setText("Количество простых чисел меньше 10000: $counter")
    }
    fun FactorialCycle(number:Int):Long{
        var counter:Long=1
        var result:Long=1
        while(counter<=number){
            result=result*counter
            counter++
        }
        return result;
    }
    fun FactorialDiapason(number:Int):Long{
        val range=1..number
        var result:Long=1;
        range.forEach(){
            result*=it
        }
        return result
    }
    fun FactorialRecusrion(number:Int):Long{
        if(number==1) return 1
        return number * FactorialRecusrion(number-1)
    }
    fun isPrime(number:Int):Boolean{

        val maxValue:Double= Math.sqrt(number.toDouble());
        val range=2 .. maxValue.toInt()
        range.forEach(){
            if((number%it).toInt()==0){
                return false
            }
        }
        return true
    }
}