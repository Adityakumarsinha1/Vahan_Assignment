package com.example.vahana

import com.example.vahana.API.CollegeJSON
import com.example.vahana.API.New
import retrofit2.http.GET

interface APIRequest {
    @GET("search")
    suspend fun getCollege() : List<New>
}