package com.example.vahana

import android.Manifest
import android.content.Intent
import android.graphics.Color.alpha
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vahana.API.New
import com.example.vahana.Adapter.RecyclerAdapter
import com.example.vahana.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList


const val BASE_URL = "http://universities.hipolabs.com/"
class MainActivity : AppCompatActivity(){
    lateinit var countdownTimer: CountDownTimer
    private var seconds = 10L

    private var nameList = ArrayList<String>()
    private var countryList = ArrayList<String>()
    private var linksList = ArrayList<String>()


    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//Requesting Notification Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        Intent(applicationContext,RunningService::class.java).also {
            it.action=RunningService.Actions.START.toString()
            startService(it)
        }
        makeAPIRequest()

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun makeAPIRequest() {
        binding.progressBar.visibility = View.VISIBLE

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        Log.d("@@@","1+${api}")
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getCollege()

                Log.d("@@@@", "2+$response")
                for (article in response) {
                    Log.d("@@@", "3+Result + $article")
                    addToList(article)
                }

                //updates ui when data has been retrieved
                withContext(Dispatchers.Main) {
                    setUpRecyclerView()
                    binding.progressBar.visibility = View.GONE
                }
            }
            catch (e: Exception) {
                Log.d("@@@", "4+$e")
                withContext(Dispatchers.Main) {
                    Log.d("@@@", "2+${api.getCollege()}")
                    attemptRequestAgain(seconds)
                }
            }

        }
    }

    private fun attemptRequestAgain(seconds: Long) {
        countdownTimer = object: CountDownTimer(seconds*1010,1000){
            override fun onFinish() {
                makeAPIRequest()
                countdownTimer.cancel()
            }
            override fun onTick(millisUntilFinished: Long) {
            }
        }
        countdownTimer.start()
    }

    private fun setUpRecyclerView() {


        binding.rvRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvRecyclerView.adapter = RecyclerAdapter(nameList, countryList,linksList)
        attemptRequestAgain(seconds)
    }

    //adds the items to our recyclerview
    private fun addToList(title:New) {
        linksList.addAll(title.web_pages)
        nameList.add(title.name)
        countryList.add(title.country)
        Log.d("@@@","5+$title")
    }

}