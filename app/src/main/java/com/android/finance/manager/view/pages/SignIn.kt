package com.android.finance.manager.view.pages

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.android.finance.manager.model.SignInState
import com.android.finance.manager.model.getUserFromTokenId
import com.android.finance.manager.ui.components.GoogleBtn
import com.google.firebase.BuildConfig
import com.google.firebase.analytics.FirebaseAnalytics

@Composable
fun SignIn(
  state: SignInState,
  navController: NavController,
  firebaseAnalytics: FirebaseAnalytics
) {
  val webClientID = ""

  fun onTokenIdReceived(tokenId: String) {
    Log.d("SignInGoogle", "Google tokenId: $tokenId")
    if (getUserFromTokenId(tokenId) != null) {
      navController.navigate("home")

      firebaseAnalytics.logEvent("sign_in_success", Bundle().apply {
        putString("token_id", tokenId)
      })
    } else {
      Log.d("SignInGoogle", "User not found")
      firebaseAnalytics.logEvent("sign_in_failure", Bundle().apply {
        putString("token_id", null)
      })
    }
  }

  fun onDialogDismissed(message: String) {
    Log.d("SignInGoogle", "Dialog dismissed: $message")

    firebaseAnalytics.logEvent("sign_in_dialog_dismissed", Bundle().apply {
      putString("message", message)
    })
  }

  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    GoogleBtn(
      clientId = webClientID,
      state = state,
      onTokenIdReceived = { tokenId -> onTokenIdReceived(tokenId)},
      onDialogDismissed = { message -> onDialogDismissed(message) },
    )
  }
}