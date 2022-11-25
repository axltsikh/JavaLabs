package com.example.elevenlabsecondtry

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.elevenlabsecondtry.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a:A=A()
        a.setUp("firstlengthasdsadas")
        val b:A=A()
        b.setUp("secondlenght")
        val result=a>b
        Log.d(TAG, "onCreate: " + result.toString())
        Log.d(TAG, "onCreate: " + a.converter("+"))
        val menu:BottomNavigationView=findViewById(R.id.bottomMenu)
        menu.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeMenu-> {
                    changeFragment(homeFragment(), R.id.container)
                    changeFragment(ListFragment(),R.id.listContainer)
                }
                R.id.addMenu->{
                    val frame:FrameLayout=findViewById(R.id.listContainer)
                    frame.removeAllViews()
                    changeFragment(addFragment(),R.id.container)
                }
            }
            true
        })
        for(a in 'A'..'H'){
            for(b in 1..8){
                ChessBoard.cells.add(a+b.toString())
            }
        }
        val file: File = File(filesDir.toString()+"Log.txt")
        file.delete()
        file.createNewFile()
        menu.selectedItemId=R.id.homeMenu
    }
    fun changeFragment(fragment: Fragment,id:Int){
        supportFragmentManager.beginTransaction().replace(id,fragment).commit()
    }
//    fun converter(which:String)->((Double,Double..)->Double)?
//    companion object{
//        var Pieces:ArrayList<ChessPiece> = arrayListOf()
//        var cells: ArrayList<String> = arrayListOf()
//
//    }
}