package com.android.finance.manager.ui.components

import android.os.CancellationSignal
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialManagerCallback
import androidx.credentials.exceptions.ClearCredentialException
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.finance.manager.model.GoogleUser
import java.util.concurrent.Executor
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
  title: String,
  navController: NavController
) {
  val ctx = LocalContext.current
  val scope = rememberCoroutineScope()

  suspend fun logout() {
    val credential = CredentialManager.create(ctx)
    val request = ClearCredentialStateRequest()

    try {
      credential.clearCredentialState(request)
      Log.d("HandleLogout", "Credential cleared")
    } catch (e: Exception) {
      Log.e("HandleLogout", "Error clearing credential", e)
    }

    Log.d("HandleLogout", GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)
  }

  fun handleLogout() {
    scope.launch {
      logout()
      navController.navigate("login")
    }
  }

  TopAppBar(
    title = { Text(title) },
    actions = {
      IconButton(onClick = { handleLogout() }){
        Icon(
          imageVector = Icons.AutoMirrored.Filled.Logout,
          contentDescription = "Log out"
        )
      }
    },
//    navigationIcon = {
//      IconButton(onClick = { openMenu() }) {
//        Icon(
//          imageVector = Icons.Filled.Menu,
//          contentDescription = "Localized description"
//        )
//      }
//    }
  )
}