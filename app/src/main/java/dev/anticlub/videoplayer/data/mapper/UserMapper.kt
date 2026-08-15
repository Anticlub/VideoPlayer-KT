package dev.anticlub.videoplayer.data.mapper

import dev.anticlub.videoplayer.domain.models.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain(): User {
    return User(
        uid = uid,
        email = email ?: "",
        displayName =  displayName
    )
}