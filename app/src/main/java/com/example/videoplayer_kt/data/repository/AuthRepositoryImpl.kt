package com.example.videoplayer_kt.data.repository

import com.example.videoplayer_kt.data.mapper.toDomain
import com.example.videoplayer_kt.data.remote.datasource.AuthRemoteDataSource
import com.example.videoplayer_kt.domain.models.AuthErrorType
import com.example.videoplayer_kt.domain.models.AuthResult
import com.example.videoplayer_kt.domain.models.User
import com.example.videoplayer_kt.domain.repository.AuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository{
    override suspend fun login(
        email: String,
        password: String
    ): AuthResult<User> {
        return try {
            val firebaseUser = remoteDataSource.login(email, password)
            AuthResult.Success(firebaseUser.toDomain())
        } catch (e: FirebaseAuthInvalidCredentialsException){
            AuthResult.Error(AuthErrorType.INVALID_CREDENTIALS)
        } catch (e: FirebaseAuthInvalidUserException){
            AuthResult.Error(AuthErrorType.USER_NOT_FOUND)
        } catch (e: FirebaseNetworkException){
            AuthResult.Error(AuthErrorType.NETWORK_ERROR)
        } catch (e: Exception){
            AuthResult.Error(AuthErrorType.UNKNOWN)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): AuthResult<User> {
        return try {
            val firebaseUser = remoteDataSource.register(email, password)
            AuthResult.Success(firebaseUser.toDomain())
        }catch (e: FirebaseAuthUserCollisionException){
            AuthResult.Error(AuthErrorType.EMAIL_ALREADY_IN_USE)
        } catch (e: FirebaseAuthWeakPasswordException){
            AuthResult.Error(AuthErrorType.WEAK_PASSWORD)
        } catch (e: FirebaseAuthInvalidCredentialsException){
            AuthResult.Error(AuthErrorType.INVALID_EMAIL)
        } catch (e: FirebaseNetworkException){
            AuthResult.Error(AuthErrorType.NETWORK_ERROR)
        } catch (e: Exception){
            AuthResult.Error(AuthErrorType.UNKNOWN)
        }
    }

    override suspend fun logout(): AuthResult<Unit> {
        return try {
            remoteDataSource.logout()
            AuthResult.Success(Unit)
        } catch (e: Exception){
            AuthResult.Error(AuthErrorType.UNKNOWN)
        }
    }

    override fun getCurrentUser(): User? {
        return remoteDataSource.getCurrentUser()?.toDomain()
    }

    override fun observeAuthState(): Flow<User?> {
        return remoteDataSource.observeAuthState().map { it?.toDomain() }
    }

}