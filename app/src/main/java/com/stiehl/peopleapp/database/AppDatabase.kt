package com.stiehl.peopleapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stiehl.peopleapp.database.daos.PersonDAO
import com.stiehl.peopleapp.models.Person

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun personDAO(): PersonDAO
}