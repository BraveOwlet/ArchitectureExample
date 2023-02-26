package ru.braveowlet.oauth.data.di

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.braveowlet.oauth.data.api.MicrosoftOAuthApi
import ru.braveowlet.oauth.data.repositories.MicrosoftOAuthRepository
import ru.braveowlet.oauth.domain.repositories.IOAuthRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MicrosoftModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TagMicrosoftOAuthRepository

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TagMicrosoftOAuthRetrofit

    @Provides
    @Singleton
    @TagMicrosoftOAuthRetrofit
    fun provideAuthRetrofit(
        @RetrofitModule.TagAuthOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://graph.microsoft.com/v1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAuthMicrosoftApi(
        @TagMicrosoftOAuthRetrofit retrofit : Retrofit
    ): MicrosoftOAuthApi = retrofit.create(MicrosoftOAuthApi::class.java)


    @Provides
    @Singleton
    fun provideOAuthProvider() = OAuthProvider
        .newBuilder("microsoft.com")
        .addCustomParameter("prompt", "consent")
        .build()


    @Provides
    @Singleton
    @TagMicrosoftOAuthRepository
    fun provideAuthMicrosoftRepository(
        oAuthProvider : OAuthProvider,
        microsoftApi: MicrosoftOAuthApi
    ) : IOAuthRepository<OAuthProvider, AuthResult> = MicrosoftOAuthRepository(oAuthProvider, microsoftApi)


}