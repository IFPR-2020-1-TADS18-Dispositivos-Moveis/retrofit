package com.stiehl.peopleapp.models

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("first_name") var firstName: String,
    @SerializedName("last_name") var lastName: String,
    var title: String,
    var enabled: Boolean = true
) {
    var id: Long? = null

    override fun equals(other: Any?) = other is Person && this.id == other.id
}