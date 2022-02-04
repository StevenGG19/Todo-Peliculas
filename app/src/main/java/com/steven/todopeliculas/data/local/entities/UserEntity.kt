package com.steven.todopeliculas.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.steven.todopeliculas.data.model.User

@Entity
class UserEntity(
    var user: User
) {
    @PrimaryKey
    var id = 0
}