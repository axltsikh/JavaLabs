package com.example.twelvelab.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.twelvelab.BitcoinClasses.APIGsonService
import com.example.twelvelab.BitcoinClasses.BitcoinClass
import com.example.twelvelab.R
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory


class SecondFragment : Fragment() {

    lateinit var rootView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_second, container, false)
        Filler()
        return rootView
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun Filler(){
        val button: Button =rootView.findViewById(R.id.getValuesButton)
        button.setOnClickListener(View.OnClickListener {
            GlobalScope.launch {
                async { getValues() }
            }
            Log.d(TAG, "Filler: " + "Основной поток")
        })
    }
    fun getValues(){
        Log.d(TAG, "getValues: corut")
        val api=getInstance().create(APIGsonService::class.java)
        GlobalScope.launch {
            val result = api.getValues()
            result.enqueue(object : Callback<BitcoinClass> {
                override fun onResponse(
                    call: Call<BitcoinClass>,
                    response: Response<BitcoinClass>
                ) {
                    val buffer=response.body()
                    if (buffer != null) {
                        Log.d(TAG, "onResponse: " + buffer.USD)
                    }
                    val amount: Double =rootView.findViewById<EditText?>(R.id.bitcoinAmount).text.toString().toDouble()
                    var result: TextView =rootView.findViewById(R.id.firstResultTextView)
                    var priceBuffer= buffer?.USD
                    result.text=(amount* priceBuffer!!).toString()
                    result =rootView.findViewById(R.id.secondResultTextView)
                    priceBuffer= buffer?.JPY
                    result.text=(amount* priceBuffer!!).toString()
                    result =rootView.findViewById(R.id.thirdResultTextView)
                    priceBuffer= buffer?.EUR

                    result.text=(amount* priceBuffer!!).toString()
                }
                override fun onFailure(call: Call<BitcoinClass>, t: Throwable) {
                    Log.d(TAG, "onFailure: " + t.message)
                }
            })
        }
    }
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://min-api.cryptocompare.com/")
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}