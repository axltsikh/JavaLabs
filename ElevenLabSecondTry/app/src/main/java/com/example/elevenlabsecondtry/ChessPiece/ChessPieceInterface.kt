package com.example.elevenlabsecondtry.ChessPiece

import java.io.Serializable

interface ChessPieceInterface:Serializable {
    abstract var PositionLetter:Char
    abstract var PositionNumber:Int
    abstract var FullPosition:String
    abstract var Abbreviation:String
    abstract var Colour:String
    abstract var FullName:String
    public fun getMoves(buffer:ArrayList<String>):String{
        return("Функция по умолчанию")
    }
}