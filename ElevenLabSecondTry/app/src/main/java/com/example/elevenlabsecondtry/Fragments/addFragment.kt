package com.example.elevenlabsecondtry.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.elevenlabsecondtry.ChessBoard
import com.example.elevenlabsecondtry.ChessPiece.*
import com.example.elevenlabsecondtry.R

class addFragment : Fragment() {

    private lateinit var rootView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_add, container, false)
        Filler()
        return rootView
    }
    private fun Filler(){
        var spinner: Spinner =rootView.findViewById(R.id.chessSpinner)
        var adapter=ArrayAdapter.createFromResource(rootView.context,R.array.Pieces,android.R.layout.simple_list_item_1)
        spinner.adapter=adapter;
        spinner=rootView.findViewById(R.id.ColorSpinner)
        adapter= ArrayAdapter.createFromResource(rootView.context,R.array.Colors,android.R.layout.simple_list_item_1)
        spinner.adapter=adapter
        val button:Button=rootView.findViewById(R.id.CreateButton)
        button.setOnClickListener(View.OnClickListener {
            Create()
        })
        val buttonauto:Button=rootView.findViewById(R.id.autoFill)
        buttonauto.setOnClickListener(View.OnClickListener {
            autoFill()
        })
    }
    private fun autoFill(){
        var piece:ChessPiece;
        piece=ChessPiece("K",'E',1,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("Q",'D',1,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("B",'C',1,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("B",'F',1,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("Kn",'G',1,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("Kn",'B',1,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("R",'A',1,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("R",'H',1,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'A',2,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'B',2,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'C',2,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'D',2,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'E',2,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'F',2,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'G',2,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'H',2,"White")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("K",'E',8,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("Q",'D',8,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("B",'C',8,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("B",'F',8,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("Kn",'G',8,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("Kn",'B',8,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("R",'A',8,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("R",'H',8,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'A',7,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'B',7,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'C',7,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'D',7,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'E',7,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'F',7,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'G',7,"Red")
        ChessBoard.Pieces.add(piece)
        piece=ChessPiece("P",'H',7,"Red")
        ChessBoard.Pieces.add(piece)
    }
    private fun Create(){
        var amountOfPieceFlag=true
        val piece:ChessPiece;
        val pieceSpinner: Spinner =rootView.findViewById(R.id.chessSpinner)
        val colorSpinner: Spinner =rootView.findViewById(R.id.ColorSpinner)
        val positionEditText=rootView.findViewById<EditText>(R.id.PositionEditText)
        val position:String=positionEditText.text.toString().uppercase()
        if(ChessBoard.Pieces.any { it.FullPosition==position}){
            Toast.makeText(rootView.context,"Позиция занята!",Toast.LENGTH_SHORT).show()
            return;
        }
        piece = when(pieceSpinner.selectedItem){
            "King"-> ChessPiece("K",position[0],position[1].digitToInt(),colorSpinner.selectedItem.toString())
            "Queen"-> ChessPiece("Q",position[0],position[1].digitToInt(),colorSpinner.selectedItem.toString())
            "Rook"-> ChessPiece("R",position[0],position[1].digitToInt(),colorSpinner.selectedItem.toString())
            "Bishop"-> ChessPiece("B",position[0],position[1].digitToInt(),colorSpinner.selectedItem.toString())
            "Knight"-> ChessPiece("Kn",position[0],position[1].digitToInt(),colorSpinner.selectedItem.toString())
            else-> ChessPiece("P",position[0],position[1].digitToInt(),colorSpinner.selectedItem.toString())
        }
        when(piece.Abbreviation){
            "K"->if(ChessBoard.Pieces.filter { it.Colour==piece.Colour && it.Abbreviation==piece.Abbreviation }.size>=1) amountOfPieceFlag=false
            "Q"->if(ChessBoard.Pieces.filter { it.Colour==piece.Colour && it.Abbreviation==piece.Abbreviation }.size>=1) amountOfPieceFlag=false
            "R"->if(ChessBoard.Pieces.filter { it.Colour==piece.Colour && it.Abbreviation==piece.Abbreviation }.size>=2) amountOfPieceFlag=false
            "B"->if(ChessBoard.Pieces.filter { it.Colour==piece.Colour && it.Abbreviation==piece.Abbreviation }.size>=2) amountOfPieceFlag=false
            "Kn"->if(ChessBoard.Pieces.filter { it.Colour==piece.Colour && it.Abbreviation==piece.Abbreviation }.size>=2) amountOfPieceFlag=false
            "P"->if(ChessBoard.Pieces.filter { it.Colour==piece.Colour && it.Abbreviation==piece.Abbreviation }.size>=8) amountOfPieceFlag=false
            else->amountOfPieceFlag=true
        }
        if(amountOfPieceFlag) {
            ChessBoard.Pieces.add(piece)
            piece.status.statusCheck()
        }
        else{
            Toast.makeText(rootView.context,"Нельзя добавить еще такую фигуру!",Toast.LENGTH_SHORT).show()
            return
        }
    }

}