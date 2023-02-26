package ru.braveowlet.oauth.data.di

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.braveowlet.oauth.data.api.YandexOAuthApi
import ru.braveowlet.oauth.data.repositories.YandexOAuthRepository
import ru.braveowlet.oauth.domain.repositories.IOAuthRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class YandexModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TagYandexOAuthRepository

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TagYandexOAuthRetrofit

    @Provides
    @Singleton
    @TagYandexOAuthRetrofit
    fun provideAuthRetrofit(
        @RetrofitModule.TagAuthOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://login.yandex.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAuthYandexApi(
        @TagYandexOAuthRetrofit retrofit : Retrofit
    ): YandexOAuthApi = retrofit.create(YandexOAuthApi::class.java)

    @Provides
    @Singleton
    fun provideYandexAuthSdk(
        @ApplicationContext context : Context
    ) : YandexAuthSdk {
        return YandexAuthSdk(
            context,
            YandexAuthOptions.Builder(context)
                .enableLogging()
                .build()
        )
    }

    @Provides
    @Singleton
    fun provideYandexAuthLoginOptions() = YandexAuthLoginOptions.Builder().build()

    @Provides
    @Singleton
    @TagYandexOAuthRepository
    fun provideAuthYandexRepository(
        yandexSDK: YandexAuthSdk,
        yandexAuthLoginOptions: YandexAuthLoginOptions,
        yandexApi : YandexOAuthApi
    ) : IOAuthRepository<Intent, ActivityResult> = YandexOAuthRepository(
        yandexSDK,
        yandexAuthLoginOptions,
        yandexApi
    )

}