package com.stiehl.peopleapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class Person(
    @ColumnInfo(name = "first_name") var firstName: String,
    @ColumnInfo(name = "last_name") var lastName: String,
    var title: String,
    var enabled: Boolean = true
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    override fun equals(other: Any?) = other is Person && this.id == other.id
}