package com.android.finance.manager.view.pages

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

@Composable
fun SignIn(state: SignInState, navController: NavController) {
  // var user: GoogleUser? by remember { mutableStateOf(null) }
  val webClientID = ""
  val androidClientID = ""

  // use webClientID for web client and androidClientID for android client
  val client = if (BuildConfig.DEBUG) webClientID else androidClientID

  fun onTokenIdReceived(tokenId: String) {
    Log.d("SignInGoogle", "Google tokenId: $tokenId")
    if (getUserFromTokenId(tokenId) != null) {
      // user = getUserFromTokenId(tokenId)
      navController.navigate("home")
    }
  }

  fun onDialogDismissed(message: String) {
    Log.d("SignInGoogle", "Dialog dismissed: $message")
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