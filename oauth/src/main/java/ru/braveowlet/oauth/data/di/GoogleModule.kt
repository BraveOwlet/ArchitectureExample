package ru.braveowlet.oauth.data.di

import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.braveowlet.oauth.BuildConfig
import ru.braveowlet.oauth.data.repositories.GoogleOAuthRepository
import ru.braveowlet.oauth.domain.repositories.IOAuthRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class GoogleModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TagGoogleOAuthRepository

    @Provides
    @Singleton
    fun provideGoogleAuthSignInClient(@ApplicationContext context: Context) = Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun provideGoogleAuthBeginSignInRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false)
                //.setFilterByAuthorizedAccounts(true)
                .build())
        .build()

    @Provides
    @Singleton
    @TagGoogleOAuthRepository
    fun provideAuthGoogleRepository(
        firebaseAuth: FirebaseAuth,
        signInClient: SignInClient,
        beginSignInRequest: BeginSignInRequest
    ) : IOAuthRepository<IntentSenderRequest, ActivityResult> = GoogleOAuthRepository(
        firebaseAuth, signInClient, beginSignInRequest
    )
}