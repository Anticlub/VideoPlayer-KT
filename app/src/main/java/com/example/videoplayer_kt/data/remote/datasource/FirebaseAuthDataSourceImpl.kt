package com.example.videoplayer_kt.data.remote.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRemoteDataSource{
    override suspend fun login(
        email: String,
        password: String
    ): FirebaseUser {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return result.user ?: throw IllegalStateException("User is null after login")
    }

    override suspend fun register(
        email: String,
        password: String
    ): FirebaseUser {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return result.user ?: throw IllegalStateException("User is null after register")
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun observeAuthState(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        firebaseAuth.addAuthStateListener(listener)

        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

}