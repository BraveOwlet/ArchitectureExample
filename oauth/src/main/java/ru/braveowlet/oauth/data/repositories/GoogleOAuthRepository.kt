package ru.braveowlet.oauth.data.repositories

import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import ru.braveowlet.oauth.data.mapper.toAuthUser
import ru.braveowlet.oauth.domain.model.AuthUser
import ru.braveowlet.oauth.domain.repositories.IOAuthRepository

internal class GoogleOAuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val signInClient : SignInClient,
    private val beginSignInRequest : BeginSignInRequest
) : IOAuthRepository<IntentSenderRequest, ActivityResult> {

    override fun getIntent(): Flow<IntentSenderRequest> = callbackFlow {
        signInClient.beginSignIn(beginSignInRequest)
            .addOnSuccessListener {
                trySend(IntentSenderRequest.Builder(it.pendingIntent.intentSender).build())
            }
            .addOnFailureListener {
                close(it)
            }
            .addOnCanceledListener {
                close(Throwable("Cancel get intent with google method"))
            }
        //awaitClose {}
    }

    override fun getToken(result: ActivityResult): Flow<String> = flow{
        val credential = signInClient.getSignInCredentialFromIntent(result.data)
        emit(credential.googleIdToken!!)
    }


    override fun getUserByToken(token: String): Flow<AuthUser> = callbackFlow {
        val firebaseCredential = GoogleAuthProvider.getCredential(token, null)
        firebaseAuth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener {
                if (it.isSuccessful && firebaseAuth.currentUser != null) {
                    trySend(firebaseAuth.currentUser?.toAuthUser()!!)
                } else {
                    close(Throwable("Failure get user with google method"))
                }
            }
            .addOnFailureListener {
                close(it)
            }
            .addOnCanceledListener {
                close(Throwable("Cancel get user with google method"))
            }
        //awaitClose {}
    }


}