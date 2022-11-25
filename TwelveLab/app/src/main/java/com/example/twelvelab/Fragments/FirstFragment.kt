package com.example.twelvelab.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.twelvelab.NewsClasses.APIService
import com.example.twelvelab.NewsClasses.Item
import com.example.twelvelab.NewsClasses.Rss
import com.example.twelvelab.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class FirstFragment : Fragment() {

    var TAG:String="ExceptionLog"
    lateinit var rootView:View
    lateinit var itemsList:ArrayList<Item>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_first, container, false)
        GlobalScope.launch {
            async{ getEvents() }
        }
        return rootView
    }
    private fun getEvents(){
        var builder:Retrofit.Builder=Retrofit.Builder().baseUrl("https://events.devby.io/rss/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
        var httpClient:OkHttpClient.Builder=OkHttpClient.Builder()
        builder.client(httpClient.build())
        var retrofit:Retrofit=builder.build()
        var apiService: APIService =retrofit.create(APIService::class.java)
        var call:Call<Rss> = apiService.callRss()
        call.enqueue(object: Callback<Rss>{
            override fun onResponse(call: Call<Rss>, response: Response<Rss>) {
                var buffer: Rss? = response.body()
                if (buffer != null) {
                    itemsList=buffer.channel.items
                }
                var recycle:RecyclerView=rootView.findViewById(R.id.firstRecycler)
                recycle.adapter= AdapterFirst(itemsList, { position->onListItemClick(position)})
            }

            override fun onFailure(call: Call<Rss>, t: Throwable) {
                Log.d(TAG, "onFailure: " + t.message)
            }
        })

    }
    fun onListItemClick(position:Int){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(Uri.parse(itemsList[position].link).toString())
        startActivity(i)
    }
}