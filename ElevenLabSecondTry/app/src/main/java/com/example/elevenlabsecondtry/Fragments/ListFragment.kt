package com.example.elevenlabsecondtry.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.elevenlabsecondtry.ChessBoard
import com.example.elevenlabsecondtry.ChessPiece.ChessPieceInterface
import com.example.elevenlabsecondtry.R


class ListFragment : Fragment() {

    private lateinit var rootView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_list, container, false)
        Filler()
        return rootView
    }
    fun Filler(){
        var counter=0
        val buffer= arrayOfNulls<ChessPieceInterface>(ChessBoard.Pieces.size)
        for (a in ChessBoard.Pieces){
            buffer.set(counter, a)
            counter++
        }
        val list:ListView=rootView.findViewById(R.id.listChess)
        val adapter= ArrayAdapter<ChessPieceInterface>(rootView.context,android.R.layout.simple_list_item_1,buffer)
        list.adapter=adapter;
        list.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val fr:Fragment= PieceDetails()
            val bundle:Bundle=Bundle()
            bundle.putSerializable("piece", ChessBoard.Pieces.get(i));
            bundle.putSerializable("class", ChessBoard.Pieces.get(i).javaClass)
            fr.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.listContainer,fr)?.addToBackStack(null)?.commit();
        })
    }
}