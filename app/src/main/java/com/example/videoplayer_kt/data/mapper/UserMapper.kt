package com.example.videoplayer_kt.data.mapper

import com.example.videoplayer_kt.domain.models.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain(): User {
    return User(
        uid = uid,
        email = email ?: "",
        displayName =  displayName
    )
}