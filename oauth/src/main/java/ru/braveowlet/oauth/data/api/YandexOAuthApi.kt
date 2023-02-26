package ru.braveowlet.oauth.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import ru.braveowlet.oauth.data.model.YandexAuthUser

internal interface YandexOAuthApi {

    @GET("info?")
    suspend fun getUserByToken(@Header("Authorization") oauth_token : String) : Response<YandexAuthUser>

}