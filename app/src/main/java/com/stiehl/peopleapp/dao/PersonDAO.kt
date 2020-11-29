package com.stiehl.peopleapp.dao

import com.stiehl.peopleapp.models.Person
import com.stiehl.peopleapp.network.services.PeopleService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PersonDAO {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(PeopleService::class.java)

    fun getAll(finished: (people: List<Person>) -> Unit) {
        service.getAll().enqueue(object : Callback<List<Person>> {
            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                val people = response.body()!!
                finished(people)
            }
            override fun onFailure(call: Call<List<Person>>, t: Throwable) { }
        })
    }

    fun get(id: Long, finished: (person: Person) -> Unit) {
        service.get(id).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                val person = response.body()!!
                finished(person)
            }
            override fun onFailure(call: Call<Person>, t: Throwable) { }
        })
    }

    fun insert(person: Person, finished: (person: Person) -> Unit) {
        service.insert(person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                val person = response.body()!!
                finished(person)
            }
            override fun onFailure(call: Call<Person>, t: Throwable) { }
        })
    }

    fun update(person: Person, finished: (person: Person) -> Unit) {
        service.update(person.id!!, person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                val person = response.body()!!
                finished(person)
            }
            override fun onFailure(call: Call<Person>, t: Throwable) { }
        })
    }

    fun save(person: Person, finished: (person: Person) -> Unit) {
        if (person.id == null)
            insert(person, finished)
        else
            update(person, finished)
    }

    fun delete(person: Person, finished: () -> Unit) {
        service.delete(person.id!!).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                finished()
            }
            override fun onFailure(call: Call<Void>, t: Throwable) { }
        })
    }
}