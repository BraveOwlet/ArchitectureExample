package ru.braveowlet.oauth.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.braveowlet.oauth.domain.model.AuthUser

internal interface IOAuthRepository<IntentType,ResultType> {

    fun getIntent() : Flow<IntentType>

    fun getToken(result : ResultType) : Flow<String>

    fun getUserByToken(token : String) : Flow<AuthUser>

}