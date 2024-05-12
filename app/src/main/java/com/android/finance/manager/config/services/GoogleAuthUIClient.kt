package com.android.finance.manager.config.services

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

class GoogleAuthUIClient {
  private val CLIENTID = "167167482782-2lo5reb76sdo2ndrei1qhj21dk3mm4el.apps.googleusercontent.com"
  private val auth = Firebase.auth

  private fun getGoogleIDOpts(): GetGoogleIdOption {
    val rawNonce = UUID.randomUUID().toString()
    val bytes = rawNonce.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    val hashedNonce = digest.fold("") { str, it ->
      str + "%02x".format(it)
    }

    return GetGoogleIdOption.Builder()
      .setFilterByAuthorizedAccounts(false)
      .setServerClientId(CLIENTID)
      .setNonce(hashedNonce)
      .build()
  }

  private fun getRequest(): GetCredentialRequest {
    return GetCredentialRequest.Builder()
      .addCredentialOption(getGoogleIDOpts())
      .build()
  }

  private suspend fun getCredentials(request: GetCredentialRequest, ctx: Context): GetCredentialResponse {
    return createCredential(ctx).getCredential(
      context = ctx,
      request = request
    )
  }

  private fun createCredential(ctx: Context): CredentialManager {
    return CredentialManager.create(ctx)
  }

  private fun getCoroutineScope(coroutine: CoroutineScope, ctx: Context, firebase: Firebase): Job {
    return coroutine.launch {
      val result = getCredentials(getRequest(), ctx)

      val credential = result.credential
      val googleTokenIdCredential = GoogleIdTokenCredential
        .createFrom(credential.data)

      val googleIdToken = googleTokenIdCredential.idToken
      val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)

      firebase.auth
        .signInWithCredential(firebaseCredential)
        .addOnCompleteListener { task ->
          if (task.isSuccessful) {

          } else {
            print("Error")
          }
        }
      print(googleIdToken)
    }
  }

  fun signIn(coroutine: CoroutineScope, ctx: Context, firebase: Firebase) {
    getCoroutineScope(coroutine, ctx, firebase)
  }

  fun signOut() {
    auth.signOut()
  }
}