package com.example.elevenlabsecondtry.ChessPiece

import android.util.Log
import com.example.elevenlabsecondtry.ChessBoard
import com.example.elevenlabsecondtry.Fragments.TAG


class ChessPiece constructor(Abbr:String,PositionLet:Char,PositionNum:Int,Color:String):ChessPieceInterface {
    override var PositionLetter: Char = 'A'
    override var PositionNumber: Int = 1
    override var FullPosition: String="A1"
    override var Abbreviation:String = "asd"
    override var Colour: String = "White"
    override var FullName:String="Pawn"
    val status:Status=Status()
    init {
        Abbreviation=Abbr
        this.PositionLetter=PositionLet
        this.PositionNumber=PositionNum
        this.FullPosition=this.PositionLetter+this.PositionNumber.toString()
        this.Colour=Color
        FullName = when(Abbreviation){
            "K"-> "King"
            "Kn"-> "Knight"
            "B"-> "Bishop"
            "Q"-> "Queen"
            "P"-> "Pawn"
            else-> "Rook"
        }
    }
    constructor(abbr:String) : this(abbr,'A',1,"Red") {
        Abbreviation=abbr
    }
    override fun toString(): String {
        return FullName + ": " + Colour + ": " + FullPosition
    }
    inner class Status{
        var CanHit:Boolean = false
        var possibleHits:ArrayList<String> = arrayListOf()
        var canBeHitted:Boolean=false
        var possibleMoves:ArrayList<String> = arrayListOf()
        fun statusCheck(){
            possibleMoves.clear()
            possibleHits.clear()
            when(Abbreviation){
                "K"-> kingMoves()
                "Kn"->knightMoves()
                "B"->bishopMoves()
                "Q"->queenMoves()
                "P"->{
                    when(Colour){
                        "Red"->redPawnMoves()
                        "White"->whitePawnMoves()
                    }
                }
                else->rookMoves()
            }
            for(a in possibleMoves){
                if(ChessBoard.Pieces.any { it.Colour!=Colour && it.FullPosition==a }){
                    CanHit=true
                    Log.d(TAG, "statusCheck: " + "asd")
                    break;
                }
                CanHit=false
            }
            for(a in ChessBoard.Pieces){
                if(a.status.possibleMoves.any{it==FullPosition} && a.Colour!=Colour){
                    canBeHitted=true
                    break
                }
                canBeHitted=false
            }
        }

        private fun redPawnMoves(){
            val charArray= charArrayOf('Z','A','B','C','D','E','F','G','H','Z')
            val intArray= intArrayOf(1,2,3,4,5,6,7,8)
            val currentLetter=charArray.indexOf(PositionLetter)
            val currentNumber=intArray.indexOf(PositionNumber)
            if(currentNumber!=0){
                if(ChessBoard.Pieces.any { it.FullPosition!= charArray[currentLetter]+intArray[currentNumber-1].toString()}){
                    possibleMoves.add(charArray[currentLetter]+intArray[currentNumber-1].toString())

                }
                if(ChessBoard.Pieces.any { it.FullPosition== charArray[currentLetter-1]+intArray[currentNumber-1].toString()}){
                    possibleMoves.add(charArray[currentLetter-1]+intArray[currentNumber-1].toString())
                }
                if(ChessBoard.Pieces.any { it.FullPosition== charArray[currentLetter+1]+intArray[currentNumber-1].toString()}){
                    possibleMoves.add(charArray[currentLetter+1]+intArray[currentNumber-1].toString())
                }
            }
        }
        private fun whitePawnMoves(){
            val charArray= charArrayOf('Z','A','B','C','D','E','F','G','H','Z')
            val intArray= intArrayOf(1,2,3,4,5,6,7,8)
            val currentLetter=charArray.indexOf(PositionLetter)
            val currentNumber=intArray.indexOf(PositionNumber)
            if(currentNumber!=7){
                if(ChessBoard.Pieces.any { it.FullPosition!= charArray[currentLetter]+intArray[currentNumber+1].toString()}){
                    possibleMoves.add(charArray[currentLetter]+intArray[currentNumber+1].toString())
                }
                if(ChessBoard.Pieces.any { it.FullPosition== charArray[currentLetter-1]+intArray[currentNumber+1].toString()}){
                    possibleMoves.add(charArray[currentLetter-1]+intArray[currentNumber+1].toString())
                }
                if(ChessBoard.Pieces.any { it.FullPosition == charArray[currentLetter+1]+intArray[currentNumber+1].toString()}){
                    possibleMoves.add(charArray[currentLetter+1]+intArray[currentNumber+1].toString())
                }
            }
        }
        private fun kingMoves(){
            val buffer= arrayListOf<String>()
            val charArray= charArrayOf('Z','A','B','C','D','E','F','G','H','Z')
            val intArray= intArrayOf(0,1,2,3,4,5,6,7,8,0)
            val currentLetter=charArray.indexOf(PositionLetter)
            val currentNumber=intArray.indexOf(PositionNumber)
            possibleMoves.add(charArray[currentLetter]+intArray[currentNumber+1].toString())
            possibleMoves.add(charArray[currentLetter]+intArray[currentNumber-1].toString())
            possibleMoves.add(charArray[currentLetter-1]+intArray[currentNumber+1].toString())
            possibleMoves.add(charArray[currentLetter-1]+intArray[currentNumber-1].toString())
            possibleMoves.add(charArray[currentLetter-1]+intArray[currentNumber].toString())
            possibleMoves.add(charArray[currentLetter+1]+intArray[currentNumber].toString())
            possibleMoves.add(charArray[currentLetter+1]+intArray[currentNumber+1].toString())
            possibleMoves.add(charArray[currentLetter+1]+intArray[currentNumber-1].toString())
            for(a in possibleMoves){
                if(ChessBoard.cells.any { it==a })
                    buffer.add(a)
                if(ChessBoard.Pieces.any { it.FullPosition==a && it.Colour==Colour })
                    buffer.remove(a)
            }
            possibleMoves=buffer
        }
        private fun knightMoves(){
            val buffer= arrayListOf<String>()
            val charArray= charArrayOf('Y','Y','Y','Y','Y','Y','A','B','C','D','E','F','G','H','Y','Y','Y','Y','Y')
            val intArray= intArrayOf(0,0,0,0,0,0,1,2,3,4,5,6,7,8,0,0,0,0,)
            val currentLetter=charArray.indexOf(PositionLetter)
            val currentNumber=intArray.indexOf(PositionNumber)
            possibleMoves.add(charArray[currentLetter+3]+intArray[currentNumber+1].toString())
            possibleMoves.add(charArray[currentLetter+3]+intArray[currentNumber-1].toString())
            possibleMoves.add(charArray[currentLetter-3]+intArray[currentNumber+1].toString())
            possibleMoves.add(charArray[currentLetter-3]+intArray[currentNumber-1].toString())
            possibleMoves.add(charArray[currentLetter+1]+intArray[currentNumber+3].toString())
            possibleMoves.add(charArray[currentLetter-1]+intArray[currentNumber+3].toString())
            possibleMoves.add(charArray[currentLetter+1]+intArray[currentNumber-3].toString())
            possibleMoves.add(charArray[currentLetter-1]+intArray[currentNumber-3].toString())
            for(a in possibleMoves){
                if(ChessBoard.cells.any { it==a })
                    buffer.add(a)
                if(ChessBoard.Pieces.any{it.FullPosition==a && it.Colour==Colour})
                    buffer.remove(a)
            }
            possibleMoves=buffer
        }
        private fun queenMoves(){
            val charArray= charArrayOf('A','B','C','D','E','F','G','H')
            val intArray= intArrayOf(1,2,3,4,5,6,7,8)
            val currentLetter=charArray.indexOf(PositionLetter)
            val currentNumber=intArray.indexOf(PositionNumber)
            for(i in currentLetter+1..7){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[i]+intArray[currentNumber].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString()}){
                    break;
                }
            }
            for(i in currentLetter-1 downTo 0){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[i]+intArray[currentNumber].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString()}){
                    break;
                }
            }
            for(i in currentNumber+1..7){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[currentLetter]+intArray[i].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[currentLetter]+intArray[i].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[currentLetter]+intArray[i].toString()}){
                    break;
                }
            }
            for(i in currentNumber-1 downTo 0) {
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[currentLetter]+intArray[i].toString() && it.Colour===Colour}){
                    break;
                }
                possibleMoves.add(charArray[currentLetter]+intArray[i].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[currentLetter]+intArray[i].toString()}){
                    break;
                }
            }
            var numBuffer:Int=currentNumber+1
            var letBuffer:Int=currentLetter+1
            while(numBuffer<8 && letBuffer<8){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[letBuffer]+intArray[numBuffer].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString()}){
                    break;
                }
                numBuffer++
                letBuffer++
            }
            numBuffer=currentNumber-1
            letBuffer=currentLetter-1
            while(numBuffer>=0 && letBuffer>=0){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[letBuffer]+intArray[numBuffer].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString()}){
                    break;
                }
                numBuffer--
                letBuffer--
            }
            numBuffer=currentNumber+1
            letBuffer=currentLetter-1
            while(numBuffer<8 && letBuffer>=0){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[letBuffer]+intArray[numBuffer].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString()}){
                    break;
                }
                numBuffer++
                letBuffer--
            }
            numBuffer=currentNumber-1
            letBuffer=currentLetter+1
            while(numBuffer>=0 && letBuffer<8){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[letBuffer]+intArray[numBuffer].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString()}){
                    break;
                }
                numBuffer--
                letBuffer++
            }
        }
        private fun rookMoves(){
            val charArray= charArrayOf('A','B','C','D','E','F','G','H')
            val intArray= intArrayOf(1,2,3,4,5,6,7,8)
            val currentLetter=charArray.indexOf(PositionLetter)
            val currentNumber=intArray.indexOf(PositionNumber)
            for(i in currentLetter+1..7){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[i]+intArray[currentNumber].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString() && it.Colour!=Colour}){
                    break;
                }
            }
            for(i in currentLetter-1 downTo 0){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[i]+intArray[currentNumber].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString()}){
                    break;
                }
            }
            for(i in currentNumber+1..7){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[currentLetter]+intArray[i].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[currentLetter]+intArray[i].toString()}){
                    break;
                }
            }
            for(i in currentNumber-1 downTo 0) {
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[i]+intArray[currentNumber].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[currentLetter]+intArray[i].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[currentLetter]+intArray[i].toString()}){
                    break;
                }
            }
        }
        private fun bishopMoves(){
            val charArray= charArrayOf('A','B','C','D','E','F','G','H')
            val intArray= intArrayOf(1,2,3,4,5,6,7,8)
            val currentLetter=charArray.indexOf(PositionLetter)
            val currentNumber=intArray.indexOf(PositionNumber)
            var numBuffer:Int=currentNumber+1
            var letBuffer:Int=currentLetter+1
            while(numBuffer<8 && letBuffer<8){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[letBuffer]+intArray[numBuffer].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString()}){
                    break;
                }
                numBuffer++
                letBuffer++
            }
            numBuffer=currentNumber-1
            letBuffer=currentLetter-1
            while(numBuffer>=0 && letBuffer>=0){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[letBuffer]+intArray[numBuffer].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString()}){
                    break;
                }
                numBuffer--
                letBuffer--
            }
            numBuffer=currentNumber+1
            letBuffer=currentLetter-1
            while(numBuffer<8 && letBuffer>=0){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[letBuffer]+intArray[numBuffer].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString()}){
                    break;
                }
                numBuffer++
                letBuffer--
            }
            numBuffer=currentNumber-1
            letBuffer=currentLetter+1
            while(numBuffer>=0 && letBuffer<8){
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString() && it.Colour==Colour}){
                    break;
                }
                possibleMoves.add(charArray[letBuffer]+intArray[numBuffer].toString())
                if(ChessBoard.Pieces.any{it.FullPosition==charArray[letBuffer]+intArray[numBuffer].toString()}){
                    break;
                }
                numBuffer--
                letBuffer++
            }
        }
    }
}