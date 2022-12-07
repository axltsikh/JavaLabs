package com.example.elevenlabsecondtry.Fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.elevenlabsecondtry.OtherTasks.ChessBoard
import com.example.elevenlabsecondtry.ChessPiece.*
import com.example.elevenlabsecondtry.R
import java.io.File

class PieceDetails : Fragment() {
    lateinit var Piece:ChessPiece
    lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_piece_details, container, false)
        val buffer:ChessPieceInterface
        Piece=arguments?.get("piece") as ChessPiece
        Filler();
        return rootView
    }
    fun Filler(){
        Piece.status.statusCheck()
        var text: TextView=rootView.findViewById(R.id.PossibleMovesTextView)
        Log.d(TAG, "Filler: " + Piece.status.possibleMoves.size)
        text.text="Возможные ходы: " + Piece.status.possibleMoves.toString()
        text=rootView.findViewById(R.id.canHitTextView)
        if(Piece.status.CanHit)
            text.text="Фигура может побить фигуру противника"
        text=rootView.findViewById(R.id.canBeHittedTextView)
        if(Piece.status.canBeHitted)
            text.text="Фигура находится под ударом"

        var button:Button=rootView.findViewById(R.id.MakeMoveButton)
        val newPosition:TextView=rootView.findViewById(R.id.makeMoveEditText)
        button.setOnClickListener(View.OnClickListener {
            val createXAnimation= ObjectAnimator.ofFloat(button,"scaleX",1.1f)
            val createYAnimation= ObjectAnimator.ofFloat(button,"scaleY",1.1f)
            createXAnimation.duration=150
            createYAnimation.duration=150
            createYAnimation.addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    val createYAnimationBack= ObjectAnimator.ofFloat(button,"scaleY",1f)
                    createYAnimationBack.duration=150
                    createYAnimationBack.start()
                }
            })
            createXAnimation.addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    val createXAnimationBack= ObjectAnimator.ofFloat(button,"scaleX",1f)
                    createXAnimationBack.duration=150
                    createXAnimationBack.start()
                }
            })
            createXAnimation.start()
            createYAnimation.start()
            val position=newPosition.text.toString().uppercase()
            Log.d(TAG, "Filler: " + position)
            if(Piece.status.possibleMoves.any { it==position }) {
                if(ChessBoard.Pieces.any { it.FullPosition== position}){
                    ChessBoard.Pieces.removeIf { it.FullPosition==position }
                }
                FileWriter(Piece,position)
                Piece.PositionLetter = position[0]
                Piece.PositionNumber = position[1].digitToInt()
                Piece.FullPosition=Piece.PositionLetter+Piece.PositionNumber.toString()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, homeFragment())?.addToBackStack(null)?.commit();
                Filler()
            }
            else{
                Toast.makeText(rootView.context,"Фигура не может сделать такой ход!",Toast.LENGTH_SHORT).show()
            }
        })
        val buttonReturn:Button= rootView.findViewById(R.id.ReturnButton)
        buttonReturn.setOnClickListener(View.OnClickListener {
            val createXAnimation= ObjectAnimator.ofFloat(buttonReturn,"scaleX",1.1f)
            val createYAnimation= ObjectAnimator.ofFloat(buttonReturn,"scaleY",1.1f)
            createXAnimation.duration=150
            createYAnimation.duration=150
            createYAnimation.addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    val createYAnimationBack= ObjectAnimator.ofFloat(buttonReturn,"scaleY",1f)
                    createYAnimationBack.duration=150
                    createYAnimationBack.start()
                }
            })
            createXAnimation.addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    val createXAnimationBack= ObjectAnimator.ofFloat(buttonReturn,"scaleX",1f)
                    createXAnimationBack.duration=150
                    createXAnimationBack.start()
                }
            })
            createXAnimation.start()
            createYAnimation.start()
            activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(R.animator.open_piece_details,
                R.animator.close_piece_details)?.replace(R.id.listContainer,ListFragment())?.commit()
        })

    }
    fun FileWriter(piece:ChessPiece,newPosition:String){
        val s:String=rootView.context.filesDir.toString()
        val file:File= File(s+"Log.txt")
        file.appendText("${piece.FullName}: ${piece.Colour} : ${piece.FullPosition} ---> ${newPosition}\n",Charsets.UTF_8)
    }
}