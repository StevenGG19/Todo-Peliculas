package com.steven.todopeliculas.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.steven.todopeliculas.data.model.User

class Converters {
    var gson = Gson()

    @TypeConverter
    fun userToString(user: User): String {
        return gson.toJson(user)
    }

    @TypeConverter
    fun stringToUser(user: String): User {
        val listType = object : TypeToken<User>() {}.type
        return gson.fromJson(user, listType)
    }
}