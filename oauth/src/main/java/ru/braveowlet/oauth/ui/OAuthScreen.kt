package ru.braveowlet.oauth.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.OAuthProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OAuthScreen() {
    val context = LocalContext.current

    val viewModel : OAuthScreenViewModel = hiltViewModel()
    val state = viewModel.viewState.value

    val launcherYandexAuth = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.setEvent(OAuthScreenContract.Event.YandexAuthResult(it))
    }
    val launcherGoogleAuth = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()){
        viewModel.setEvent(OAuthScreenContract.Event.GoogleAuthResult(it))
    }

    val launcherMicrosoft : (OAuthProvider)->Unit = {
        viewModel.firebase.startActivityForSignInWithProvider(context as Activity,it)
            .addOnSuccessListener { result ->
                viewModel.setEvent(OAuthScreenContract.Event.MicrosoftAuthResult(result))
            }
            .addOnFailureListener { error ->
                viewModel.setEvent(OAuthScreenContract.Event.AuthError(error))
            }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect {
            when(it){
                is OAuthScreenContract.Effect.GoogleAuth -> {launcherGoogleAuth.launch(it.intent)}
                is OAuthScreenContract.Effect.MicrosoftAuth -> {launcherMicrosoft(it.intent)}
                is OAuthScreenContract.Effect.YandexAuth -> {launcherYandexAuth.launch(it.intent)}
            }
        }
    }

    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            when(state){
                OAuthScreenContract.OAuthScreenState.NonAuthorized -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Button(
                            onClick = { viewModel.setEvent(OAuthScreenContract.Event.ClickButtonYandexAuth) }
                        ) {
                            Text("Sign with Yandex")
                        }
                        Button(
                            onClick = { viewModel.setEvent(OAuthScreenContract.Event.ClickButtonGoogleAuth) }
                        ) {
                            Text("Sign with Google")
                        }
                        Button(
                            onClick = { viewModel.setEvent(OAuthScreenContract.Event.ClickButtonMicrosoftAuth) }
                        ) {
                            Text("Sign with Microsoft")
                        }
                    }
                }
                is OAuthScreenContract.OAuthScreenState.FailureAuthorized -> {
                    AlertDialog(
                        onDismissRequest = {
                            viewModel.setEvent(OAuthScreenContract.Event.ClickClear)
                        }
                    ){
                        Text("Ошибка аутентификации")
                    }
                }
                OAuthScreenContract.OAuthScreenState.LoadAuthorized -> {
                    Box(
                        modifier = Modifier.align(Alignment.Center)
                    ){
                        CircularProgressIndicator()
                    }
                }
                is OAuthScreenContract.OAuthScreenState.SuccessAuthorized -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Name - ${state.user.name}")
                        Text("Name - ${state.user.email}")
                        Button(
                            onClick = {
                                viewModel.setEvent(OAuthScreenContract.Event.ClickClear)
                            }
                        ){
                            Text("Выход")
                        }
                    }
                }
            }
        }
    }
}