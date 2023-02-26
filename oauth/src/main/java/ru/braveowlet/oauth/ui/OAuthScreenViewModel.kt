package ru.braveowlet.oauth.ui

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.braveowlet.mvi.ui.MviViewModel
import ru.braveowlet.oauth.data.di.GoogleModule
import ru.braveowlet.oauth.data.di.MicrosoftModule
import ru.braveowlet.oauth.data.di.YandexModule
import ru.braveowlet.oauth.domain.repositories.IOAuthRepository
import javax.inject.Inject

@HiltViewModel
internal class OAuthScreenViewModel @Inject constructor(
    val firebase : FirebaseAuth,
    @YandexModule.TagYandexOAuthRepository private val yandexOAuthRepository: IOAuthRepository<Intent, ActivityResult>,
    @GoogleModule.TagGoogleOAuthRepository private val googleOAuthRepository: IOAuthRepository<IntentSenderRequest, ActivityResult>,
    @MicrosoftModule.TagMicrosoftOAuthRepository private val microsoftOAuthRepository: IOAuthRepository<OAuthProvider, AuthResult>
) : MviViewModel<OAuthScreenContract.Event, OAuthScreenContract.OAuthScreenState, OAuthScreenContract.Effect>() {

    override fun setInitialState(): OAuthScreenContract.OAuthScreenState = OAuthScreenContract.OAuthScreenState.NonAuthorized

    override fun handleEvents(event: OAuthScreenContract.Event) {
        when(event){
            OAuthScreenContract.Event.ClickButtonGoogleAuth -> {
                setState { OAuthScreenContract.OAuthScreenState.LoadAuthorized }
                viewModelScope.launch {
                    googleOAuthRepository.getIntent()
                        .catch {
                            setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(it) }
                        }
                        .collect{
                            setEffect { OAuthScreenContract.Effect.GoogleAuth(it) }
                        }
                }
            }
            OAuthScreenContract.Event.ClickButtonMicrosoftAuth -> {
                setState { OAuthScreenContract.OAuthScreenState.LoadAuthorized }
                viewModelScope.launch {
                    microsoftOAuthRepository.getIntent()
                        .catch {
                            setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(it) }
                        }
                        .collect{
                            setEffect { OAuthScreenContract.Effect.MicrosoftAuth(it) }
                        }
                }
            }
            OAuthScreenContract.Event.ClickButtonYandexAuth -> {
                setState { OAuthScreenContract.OAuthScreenState.LoadAuthorized }
                viewModelScope.launch {
                    yandexOAuthRepository.getIntent()
                        .catch {
                            setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(it) }
                        }
                        .collect{
                            setEffect { OAuthScreenContract.Effect.YandexAuth(it) }
                        }
                }
            }
            is OAuthScreenContract.Event.GoogleAuthResult -> {
                viewModelScope.launch {
                    googleOAuthRepository.getToken(event.result)
                        .catch {
                            setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(it) }
                        }
                        .collect{
                            googleOAuthRepository.getUserByToken(it)
                                .catch {
                                    setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(it) }
                                }
                                .collect{
                                    setState { OAuthScreenContract.OAuthScreenState.SuccessAuthorized(it)}
                                }
                        }
                }
            }
            is OAuthScreenContract.Event.MicrosoftAuthResult -> {
                viewModelScope.launch {
                    microsoftOAuthRepository.getToken(event.result)
                        .catch {
                            setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(it) }
                        }
                        .collect{
                            microsoftOAuthRepository.getUserByToken(it)
                                .catch {
                                    setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(it) }
                                }
                                .collect{
                                    setState { OAuthScreenContract.OAuthScreenState.SuccessAuthorized(it)}
                                }
                        }
                }
            }
            is OAuthScreenContract.Event.YandexAuthResult -> {
                viewModelScope.launch {
                    yandexOAuthRepository.getToken(event.result)
                        .catch {
                            setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(it) }
                        }
                        .collect{
                            yandexOAuthRepository.getUserByToken(it)
                                .catch {
                                    setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(it) }
                                }
                                .collect{
                                    setState { OAuthScreenContract.OAuthScreenState.SuccessAuthorized(it)}
                                }
                        }
                }
            }
            is OAuthScreenContract.Event.AuthError -> {
                setState { OAuthScreenContract.OAuthScreenState.FailureAuthorized(event.exception) }
            }
            OAuthScreenContract.Event.ClickClear -> {
                setState { OAuthScreenContract.OAuthScreenState.NonAuthorized}
            }
        }
    }
}