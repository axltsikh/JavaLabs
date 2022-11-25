package com.example.tenlabrightdirectory.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.tenlabrightdirectory.R

class LastFragment : Fragment() {
    lateinit var rootView:View
    lateinit var text:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView=inflater.inflate(R.layout.fragment_last,container,false);
        var but:Button=rootView.findViewById<Button>(R.id.List);
        but.setOnClickListener(View.OnClickListener {
            List()
        })
        but=rootView.findViewById<Button>(R.id.Map);
        but.setOnClickListener(View.OnClickListener {
            Map()
        })
        text=rootView.findViewById(R.id.textaaaa)
        return rootView;
    }

    fun List(){
        text.setText("")
        var list=listOf(1,2,6,4,5)
        list+=2;
        text.setText(list.toString())
        val oddList =list.filter { it%2!=0 }
        text.append("Только нечётные числа: $oddList \n")
        val distinctList=list.distinct();
        text.append("Только уникальные числа: $distinctList \n")
        text.append("Простые числа: " + list.filter(::isPrime) + "\n");
        val findlist=list.find { it%3==0 }
        text.append("Первое число кратное 3: $findlist \n")
        val groupList=list.groupBy {  }
        text.append("Группировка: " + groupList.toString() + "\n")
        val allList=list.all { it>=1 }
        text.append("Все элементы больше 1? " + allList.toString()+"\n")
        val anyList=list.any { it == 6 }
        text.append("Есть ли элемент равный 6? " + anyList.toString()+"\n")
        val (firstDestruct,secondDestruct)=list
        text.append("Деструктуризация: \n Первый элемент: $firstDestruct \nВторой элемент: $secondDestruct\n")
        var a:Int=5
        text.append("Лямбда-выражение: ")
        text.append(containsIn(list,a,{x:Collection<Int>,y:Int->x.any { it==y }}).toString());
        a=8
        text.append(containsIn(list,a,{x:Collection<Int>,y:Int->x.any { it==y }}).toString());
    }
    fun Map(){
        text.setText("")
        var marksandname=mutableMapOf("Фамилия1" to 20,"Фамилия2" to 23,"Фамилия3" to 25,"Фамилия4" to 30,
            "Фамилия5" to 33,"Фамилия6" to 37,"Фамилия7" to 15,"Фамилия8" to 36,"Фамилия9" to 39,"Фамилия10" to 21,
            "Фамилия11" to 19,"Фамилия12" to 10,"Фамилия13" to 24,"Фамилия14" to 35,"Фамилия15" to 31,"Фамилия16" to 29,)
        text.append("Изначальный Map: $marksandname.toString() \n")
        marksandname.forEach(){
            when(it.value){
                40-> marksandname[it.key] = 10
                39->marksandname[it.key] = 9
                38->marksandname[it.key] = 8
                37,36,35->marksandname[it.key] = 7
                34,33,32->marksandname[it.key] = 6
                31,30,29->marksandname[it.key] = 5
                28,27,26,25->marksandname[it.key] = 4
                24,23,22->marksandname[it.key] = 3
                21,20,19->marksandname[it.key] = 2
                else->marksandname[it.key] = 1
            }
        }
        text.append("Map после преобразования:  " + marksandname.toString() + "\n")
        if(marksandname.any { it.value<4 }){
            text.append("Неудовлетворительные оценки существуют \n")
        }
        for(a in 1..10){
            text.append("Количество оценок $a: " + marksandname.filter { it.value==a }.size + "\n")
        }
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
    fun containsIn(collection: Collection<Int>,value:Int,op:(Collection<Int>,Int)->Boolean):Boolean = op(collection,value)
}