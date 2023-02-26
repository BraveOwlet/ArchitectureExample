package ru.braveowlet.oauth.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RetrofitModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TagAuthHttpLoggingInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TagAuthOkHttpClient

    @Provides
    @Singleton
    @TagAuthHttpLoggingInterceptor
    fun provideAuthHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @TagAuthOkHttpClient
    fun  provideAuthOkHttpClient(
        @TagAuthHttpLoggingInterceptor httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

}