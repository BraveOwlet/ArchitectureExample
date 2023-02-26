package ru.braveowlet.oauth.data.repositories

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.braveowlet.oauth.data.api.MicrosoftOAuthApi
import ru.braveowlet.oauth.data.mapper.toAuthUser
import ru.braveowlet.oauth.domain.model.AuthUser
import ru.braveowlet.oauth.domain.repositories.IOAuthRepository

internal class MicrosoftOAuthRepository(
    private val provider: OAuthProvider,
    private val microsoftOAuthApi : MicrosoftOAuthApi,
) : IOAuthRepository<OAuthProvider, AuthResult> {

    override fun getIntent(): Flow<OAuthProvider> = flow{
        emit(provider)
    }

    override fun getToken(result: AuthResult): Flow<String> = flow {
        emit((result.credential as OAuthCredential).accessToken!!)
    }

    override fun getUserByToken(token: String): Flow<AuthUser> = flow {
        emit(microsoftOAuthApi.getUserByToken(token).body()?.toAuthUser()!!)
    }

}