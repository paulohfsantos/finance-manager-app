package com.android.finance.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.finance.manager.model.SignInState
import com.android.finance.manager.view.pages.IndexScreen
import com.android.finance.manager.view.pages.SignIn
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
  private val firebase: Firebase = Firebase
  override fun onCreate(savedInstanceState: Bundle?) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

    super.onCreate(savedInstanceState)
    setContent {
      MyApp(firebaseAnalytics)
    }
  }

  @Composable
  fun MyApp(firebaseAnalytics: FirebaseAnalytics) {
    val navController = rememberNavController()
    val state = remember { SignInState() }

    fun setupScreenTracking() {
      navController.addOnDestinationChangedListener { _, destination, _ ->
        val params = Bundle().apply {
          putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.label as String?)
          putString(FirebaseAnalytics.Param.SCREEN_CLASS, destination.label as String?)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
      }
    }

    setupScreenTracking() // setup screen tracking

    NavHost(navController = navController, startDestination = "login") {
      composable("login") {
        SignIn(state = state, navController = navController, firebaseAnalytics = firebaseAnalytics)
      }
      composable("home") {
        IndexScreen(user = firebase.auth.currentUser, firebaseAnalytics = firebaseAnalytics)
      }
    }
  }
}