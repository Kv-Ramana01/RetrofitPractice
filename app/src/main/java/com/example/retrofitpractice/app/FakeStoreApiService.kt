package com.example.retrofitpractice.app
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApiService {

    @GET("users/{userId}")
    suspend fun getUserById(@Path("userId") userId: Int): Response<User>

}