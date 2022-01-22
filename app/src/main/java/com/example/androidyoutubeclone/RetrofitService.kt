package com.example.androidyoutubeclone

import android.app.Person
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("/youtube/list")
    fun getYoutubeList(): Call<ArrayList<Youtube>>
}