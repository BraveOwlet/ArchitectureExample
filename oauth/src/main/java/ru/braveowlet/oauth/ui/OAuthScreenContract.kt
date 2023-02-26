package ru.braveowlet.oauth.ui

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.OAuthProvider
import ru.braveowlet.mvi.ui.IViewEffect
import ru.braveowlet.mvi.ui.IViewEvent
import ru.braveowlet.mvi.ui.IViewState
import ru.braveowlet.oauth.domain.model.AuthUser

internal class OAuthScreenContract {

    sealed class OAuthScreenState : IViewState{
        object NonAuthorized : OAuthScreenState()
        object LoadAuthorized : OAuthScreenState()
        class SuccessAuthorized(val user : AuthUser) : OAuthScreenState()
        class FailureAuthorized(val exception: Throwable) : OAuthScreenState()
    }

    sealed class Event : IViewEvent {
        object ClickClear : Event()
        object ClickButtonYandexAuth : Event()
        object ClickButtonGoogleAuth : Event()
        object ClickButtonMicrosoftAuth : Event()
        class YandexAuthResult(val result : ActivityResult) : Event()
        class GoogleAuthResult(val result : ActivityResult) : Event()
        class MicrosoftAuthResult(val result : AuthResult) : Event()
        class AuthError(val exception: Throwable) : Event()
    }

    sealed class Effect : IViewEffect {
        class YandexAuth(val intent: Intent) : Effect()
        class GoogleAuth(val intent: IntentSenderRequest) : Effect()
        class MicrosoftAuth(val intent: OAuthProvider) : Effect()
    }

}