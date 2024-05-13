package com.android.finance.manager

import CameraScreen
import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.finance.manager.model.SignInState
import com.android.finance.manager.view.pages.IndexScreen
import com.android.finance.manager.view.pages.SignIn
import com.android.finance.manager.view.pages.documentList
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyApp()
    }
  }

  @OptIn(ExperimentalPermissionsApi::class)
  @Preview
  @Composable
  fun MyApp() {
    val navController = rememberNavController()
    val state = remember { SignInState() }
    val cameraPermissionState: PermissionState = rememberPermissionState(
      Manifest.permission.CAMERA
    )

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
        if (cameraPermissionState.status.isGranted) {
          CameraScreen()
        } else {
          Toast.makeText(
            this@MainActivity,
            "Permission denied",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    }
  }
}