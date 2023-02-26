package ru.braveowlet.oauth.data.repositories

import android.content.Intent
import androidx.activity.result.ActivityResult
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthSdk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.braveowlet.oauth.data.api.YandexOAuthApi
import ru.braveowlet.oauth.data.mapper.toAuthUser
import ru.braveowlet.oauth.domain.model.AuthUser
import ru.braveowlet.oauth.domain.repositories.IOAuthRepository

internal class YandexOAuthRepository(
    private val yandexSDK : YandexAuthSdk,
    private val yandexAuthLoginOptions : YandexAuthLoginOptions,
    private val yandexOAuthApi : YandexOAuthApi
) : IOAuthRepository<Intent, ActivityResult>{

    override fun getIntent(): Flow<Intent> = flow {
        emit(yandexSDK.createLoginIntent(yandexAuthLoginOptions))
    }

    override fun getToken(result: ActivityResult): Flow<String> = flow {
        emit(yandexSDK.extractToken(result.resultCode, result.data)?.value!!)
    }

    override fun getUserByToken(token: String): Flow<AuthUser> = flow {
        emit(yandexOAuthApi.getUserByToken("OAuth $token").body()?.toAuthUser()!!)
    }

}