package com.stiehl.peopleapp.network.services

import com.stiehl.peopleapp.models.Person
import retrofit2.Call
import retrofit2.http.*

interface PeopleService {
    @GET("people")
    fun getAll(): Call<List<Person>>

    @GET("people/{id}")
    fun get(@Path("id") id: Long): Call<Person>

    @POST("people")
    @Headers("Content-Type: application/json")
    fun insert(@Body person: Person): Call<Person>

    @PATCH("people/{id}")
    @Headers("Content-Type: application/json")
    fun update(@Path("id") id: Long, @Body person: Person): Call<Person>

    @DELETE("people/{id}")
    fun delete(@Path("id") id: Long): Call<Void>
}