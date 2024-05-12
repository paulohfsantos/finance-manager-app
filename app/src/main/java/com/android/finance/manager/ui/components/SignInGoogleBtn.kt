package com.android.finance.manager.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavController
import com.android.finance.manager.config.services.GoogleAuthUIClient
import com.google.firebase.ktx.Firebase

@Composable
fun SignInGoogleBtn(navController: NavController) {
  val googleAuthUIClient = GoogleAuthUIClient()
  val ctx = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  val handleSignIn: () -> Unit = {
    try {
      googleAuthUIClient.signIn(coroutineScope, ctx, Firebase)
      Log.i("SignInGoogleBtn", "Sign in with Google")
      Toast.makeText(ctx, "Sign in with Google", Toast.LENGTH_SHORT).show()
      navController.navigate("home")
    } catch (e: GetCredentialException) {
      Log.e("SignInGoogleBtn", "Error signing in with Google", e)
      Toast.makeText(ctx, "Error signing in with Google", Toast.LENGTH_SHORT).show()
    }
  }
  Button(onClick = handleSignIn) {
    Text("Sign in with Google")
  }
}