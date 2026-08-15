package dev.anticlub.videoplayer.data.repository

import dev.anticlub.videoplayer.data.mapper.toDomain
import dev.anticlub.videoplayer.data.remote.datasource.AuthRemoteDataSource
import dev.anticlub.videoplayer.domain.models.AuthErrorType
import dev.anticlub.videoplayer.domain.models.DomainException
import dev.anticlub.videoplayer.domain.models.User
import dev.anticlub.videoplayer.domain.repository.AuthRepository
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
    ): User {
        return try {
            val firebaseUser = remoteDataSource.login(email, password)
            firebaseUser.toDomain()
        } catch (e: FirebaseAuthInvalidCredentialsException){
            throw DomainException.AuthException(AuthErrorType.INVALID_CREDENTIALS)
        } catch (e: FirebaseAuthInvalidUserException){
            throw DomainException.AuthException(AuthErrorType.USER_NOT_FOUND)
        } catch (e: FirebaseNetworkException){
            throw DomainException.AuthException(AuthErrorType.NETWORK_ERROR)
        } catch (e: Exception){
            throw DomainException.AuthException(AuthErrorType.UNKNOWN)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): User {
        return try {
            val firebaseUser = remoteDataSource.register(email, password)
            firebaseUser.toDomain()
        }catch (e: FirebaseAuthUserCollisionException){
            throw DomainException.AuthException(AuthErrorType.EMAIL_ALREADY_IN_USE)
        } catch (e: FirebaseAuthWeakPasswordException){
            throw DomainException.AuthException(AuthErrorType.WEAK_PASSWORD)
        } catch (e: FirebaseAuthInvalidCredentialsException){
            throw DomainException.AuthException(AuthErrorType.INVALID_EMAIL)
        } catch (e: FirebaseNetworkException){
            throw DomainException.AuthException(AuthErrorType.NETWORK_ERROR)
        } catch (e: Exception){
            throw DomainException.AuthException(AuthErrorType.UNKNOWN)
        }
    }

    override suspend fun logout() {
        try {
            remoteDataSource.logout()
        } catch (e: Exception){
            throw DomainException.AuthException(AuthErrorType.UNKNOWN)
        }
    }

    override fun getCurrentUser(): User? {
        return remoteDataSource.getCurrentUser()?.toDomain()
    }

    override fun observeAuthState(): Flow<User?> {
        return remoteDataSource.observeAuthState().map { it?.toDomain() }
    }

}