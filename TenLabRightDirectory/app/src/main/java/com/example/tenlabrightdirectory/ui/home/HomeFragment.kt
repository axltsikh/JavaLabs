package com.example.tenlabrightdirectory.ui.home

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
import com.example.tenlabrightdirectory.databinding.ActivityMainBinding.inflate
import com.example.tenlabrightdirectory.databinding.FragmentHomeBinding
val valString:String="15"
var varString="varString"
val valInt=5
var varInt:Int=5
class HomeFragment : Fragment() {
    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root=inflater.inflate(R.layout.fragment_home,container,false);
        var but:Button=root.findViewById(R.id.Result)
        but.setOnClickListener(View.OnClickListener {
            result()
        })
        but=root.findViewById(R.id.Email)
        but.setOnClickListener(View.OnClickListener {
            email()
        })
        return root;
    }
    fun result(){
        val text:TextView=root.findViewById(R.id.constText)
        val const:String=root.findViewById<EditText>(R.id.First).text.toString()
        val variable:String=root.findViewById<EditText>(R.id.Second).text.toString()
        text.setText("Константа Int? : $const\nПеременная Int? : $variable")
        text.append("\nПреобразования: ${valInt.toString()} \n ${valString.toInt()}")
    }
    fun email(){
        val email=root.findViewById<EditText>(R.id.email)
        val password=root.findViewById<EditText>(R.id.password)
        val text=root.findViewById<TextView>(R.id.emailTextView)
        if(isValid(email.text.toString(),password.text.toString(),text))
            text.setText("Данные введены правильно")
    }
    fun isValid(email: String,password: String,text:TextView):Boolean{

        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        fun notNull():Boolean=!(email.isNullOrEmpty() || password.isNullOrEmpty())
        if(password.length<6 || password.length>12){
            text.setText("Неверная длина пароля!")
            return false
        }
        password.forEach {
            if(it==' '){
                text.setText("Пароль не может содержать пробелы")
                return false;
            }
        }
        if(!EMAIL_REGEX.toRegex().matches(email)) {
            text.setText("Неверный формат Email")
            return false
        }
        return true
    }
}