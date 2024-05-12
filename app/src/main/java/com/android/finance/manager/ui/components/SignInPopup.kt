package com.android.finance.manager.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.android.finance.manager.model.SignInState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch

@Composable
fun SignInPopup(
  state: SignInState,
  clientId: String,
  onTokenIdReceived: (String) -> Unit,
  onDialogDismissed: (String) -> Unit,
) {
  val scope = rememberCoroutineScope()
  val context = LocalContext.current
  val credentialManager = remember { CredentialManager.create(context) }

  val googleIdOption = remember {
    GetGoogleIdOption.Builder()
      .setServerClientId(clientId)
      .setFilterByAuthorizedAccounts(false)
      .build()
  }

  val request = remember {
    GetCredentialRequest
      .Builder()
      .setCredentialOptions(
        listOf(googleIdOption)
      )
      .build()
  }

  LaunchedEffect(key1 = state.opened) {
    if (state.opened) {
      scope.launch {
        Log.d("GoogleSignIn", "Requesting Google Credential")
        try {
          val response = credentialManager.getCredential(
            request = request,
            context = context
          )

          Log.i("GoogleSignInResponse", "Response: $response")

          handleSignIn(credentialResponse = response,
            onTokenIdReceived = { tokenId ->
              Log.d("GoogleSignIn", "Google tokenId: $tokenId")
              onTokenIdReceived(tokenId)
              state.close()
            },
            onDialogDismissed = { message ->
              Log.d("GoogleSignIn", "Dialog dismissed: $message")
              onDialogDismissed(message)
              state.close()
            })
        } catch (e: Exception) {
          Log.e("GoogleSignIn", "banana: ${e.message}")
          state.close()
        }
      }
    }
  }
}

private fun handleSignIn(
  credentialResponse: GetCredentialResponse,
  onTokenIdReceived: (String) -> Unit,
  onDialogDismissed: (String) -> Unit,
) {
  when (val credential = credentialResponse.credential) {
    is CustomCredential -> {
      if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        try {
          val googleIdTokenCredential = GoogleIdTokenCredential
            .createFrom(credential.data)
          onTokenIdReceived(googleIdTokenCredential.idToken)
        } catch (e: GoogleIdTokenParsingException) {
          onDialogDismissed("Invalid Google tokenId response: ${e.message}")
        }
      } else {
        onDialogDismissed("Unexpected Type of Credential.")
      }
    }

    else -> {
      onDialogDismissed("Unexpected Type of Credential.")
    }
  }
}