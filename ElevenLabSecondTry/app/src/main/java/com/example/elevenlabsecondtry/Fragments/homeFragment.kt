package com.example.elevenlabsecondtry.Fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.elevenlabsecondtry.OtherTasks.ChessBoard
import com.example.elevenlabsecondtry.R

var TAG:String="ExceptionLog"
class homeFragment : Fragment() {
    private lateinit var rootView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        Filler()
        return rootView
    }
    private fun Filler(){
        var counter=8
        val charArray= charArrayOf('Z','A','B','C','D','E','F','G','H')
        val board: TableLayout =rootView.findViewById(R.id.ChessBoard)
        for(row in 0..8){
            val tableRow=TableRow(rootView.context)
            var secondCounter=0
            for(column in charArray){
                val text=TextView(rootView.context)
                text.width=50
                text.height=55
                text.textSize=25f
                text.text=" "
                if(column=='Z' && row!=8){
                    text.text=counter.toString()
                }
                if(row==8 && column!='Z'){
                    text.text=column.toString()
                }
                for(a in ChessBoard.Pieces){
                    if(a.PositionLetter==column && a.PositionNumber==counter)
                    {
                        when(a.Colour){
                            "Red"->text.setTextColor(Color.parseColor("#E3242B"));
                            else->text.setTextColor(Color.parseColor("#FFFFFF"));
                        }
                        if(a.Abbreviation.equals("Kn"))
                            text.textSize=22f
                        text.text=a.Abbreviation
                    }
                }
                tableRow.addView(text,secondCounter)
                secondCounter++
            }
            counter--
            board.addView(tableRow,row)
        }
    }

}