package com.example.twelvelab

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.twelvelab.Fragments.FirstFragment
import com.example.twelvelab.Fragments.SecondFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var str:String="<div><strong>Тип события: </strong>Другие</div><div><strong>Событие начнётся: </strong>01 ноября 2022</div><div><strong>Событие закончится: </strong>30 января 2023</div><div><strong>Краткое описание события:</strong></div><div><p class=\"MsoNormal\" style=\"margin-bottom:0cm;line-height:normal\"><span lang=\"ru-UA\" style=\"font-size:12.0pt;font-family:&quot;Times New Roman&quot;,serif;\n" +
                "    mso-fareast-font-family:&quot;Times New Roman&quot;;mso-fareast-language:#0C00\"><o:p></o:p></span></p>\n" +
                "    \n" +
                "    <p class=\"MsoNormal\" style=\"margin-bottom:0cm;line-height:normal\">&nbsp;</p>\n" +
                "    \n" +
                "    <p class=\"MsoNormal\" style=\"margin-bottom:0cm;line-height:normal\"><b><span lang=\"ru-UA\" style=\"font-family:&quot;Arial&quot;,sans-serif;mso-fareast-font-f...</div><br/><a href=\"https://events.devby.io/mentorskaya-programma-dlya-zhenschin-v-it\" title=\"Dev.by | Менторская программа для женщин в IT\">Читать далее</a>      "
        val delim="\\</strong>"
        val arr= Pattern.compile(delim).split(str)
        Log.d("ExceptionLog", "onCreate: " + arr[2])
        Log.d("ExceptionLog", "onCreate: " + arr[3])
        val menu:BottomNavigationView=findViewById(R.id.btm_menu)
        menu.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.FirstFragment-> {
                    ChangeFrame(FirstFragment())
                }
                R.id.SecondFragment->{
                    ChangeFrame(SecondFragment())
                }
            }
            true
        })
    }
    private fun ChangeFrame(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameContainer,fragment).commit()
    }
}