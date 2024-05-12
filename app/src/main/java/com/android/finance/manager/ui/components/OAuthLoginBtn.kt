package com.android.finance.manager.ui.components

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun OnAuthLoginBtn(navigate: NavController) {
  // val clientId = "297010592252-pe77qv4t5kmiitrva7oqi1qi1dss0o8c.apps.googleusercontent.com"
  val clientId = "167167482782-9jeb5lj83h11u627acdslhnvn7hg8p49.apps.googleusercontent.com"
  val ctx = LocalContext.current
  val firebaseAuth = Firebase.auth
  val loggedUser = firebaseAuth.currentUser

  val googleSignInClient = getGoogleLoginAuth(clientId, ctx)
  val activity = ActivityResultContracts.StartActivityForResult()

  val startForResult = rememberLauncherForActivityResult(activity) { result ->
    Log.i("RESULT", "${result.resultCode}")
    if (result.resultCode == Activity.RESULT_OK) {
      val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
      val account = task.result
      val idToken = account.idToken

      try {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
        Log.i("SignInGoogleBtn", "${loggedUser?.displayName} signed in with Google")
        navigate.navigate("home")
        Toast.makeText(ctx, "Sign in with Google", Toast.LENGTH_SHORT).show()
      } catch (e: Exception) {
        Toast.makeText(ctx, "Error signing in with Google", Toast.LENGTH_SHORT).show()
      }
    } else {
      Toast.makeText(ctx, "Error signing in with Google", Toast.LENGTH_SHORT).show()
    }
  }

  val onClickGoogleSignIn: () -> Unit = {
    startForResult.launch(googleSignInClient.signInIntent)
  }

  Button(modifier = Modifier, onClick = { onClickGoogleSignIn() }) {
    Text("entrar com google")
  }
}

fun getGoogleLoginAuth(webClient: String, context: Context): GoogleSignInClient {
  val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestEmail()
    .requestIdToken(webClient)
    .requestId()
    .requestProfile()
    .build()
  return GoogleSignIn.getClient(context, gso)
}