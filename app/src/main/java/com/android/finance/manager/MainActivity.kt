package com.android.finance.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.finance.manager.model.SignInState
import com.android.finance.manager.view.pages.CameraScreen
import com.android.finance.manager.view.pages.IndexScreen
import com.android.finance.manager.view.pages.SignIn
import com.android.finance.manager.view.pages.documentList

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyApp()
    }
  }

  @Preview
  @Composable
  fun MyApp() {
    val navController = rememberNavController()
    val state = remember { SignInState() }
    // var user: GoogleUser? by remember { mutableStateOf(null) }

    NavHost(navController = navController, startDestination = "login") {
      composable("login") {
        SignIn(
          state = state,
          navController = navController
        )
      }
      composable("home") {
        IndexScreen(
          documents = documentList,
          onItemClick = {},
          navigate = navController
        )
      }
      composable("camera") {
        CameraScreen()
      }
    }
  }
}