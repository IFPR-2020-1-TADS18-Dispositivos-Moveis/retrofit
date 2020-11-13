package com.stiehl.peopleapp.database.daos

import androidx.room.*
import com.stiehl.peopleapp.models.Person

@Dao
interface PersonDAO {
    @Query("SELECT * FROM people")
    fun getAll(): List<Person>

    @Query("SELECT * FROM people WHERE id IN (:ids)")
    fun getAllByIds(ids: IntArray): List<Person>

    @Query("SELECT * FROM people WHERE first_name LIKE :first AND last_name LIKE :last")
    fun findByName(first: String, last: String): Person

    @Insert
    fun insert(person: Person): Long

    @Insert
    fun insertAll(vararg people: Person): LongArray

    @Delete
    fun delete(person: Person)

    @Update
    fun update(person: Person)
}