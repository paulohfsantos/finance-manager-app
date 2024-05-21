package com.android.finance.manager.view.pages

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseUser


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexScreen(
  user: FirebaseUser? = null,
  firebaseAnalytics: FirebaseAnalytics
) {
  fun generateSequence() {
    print("Button 1 clicked")

    firebaseAnalytics.logEvent("button_1_clicked", Bundle().apply {
      putString("button", "button_1")
    })
  }

  Surface(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      TopAppBar(title = {
        Text(text = "Olá, ${user?.displayName ?: "Usuário"}")
      })

      Box(modifier = Modifier.weight(1f)) {
        Text(text = "Olá, ${user?.displayName ?: "Usuário"}")
      }

      Box {
        Row {
          Button(onClick = { generateSequence() }) {
            Text(text = "Button 1")
          }

          Button(onClick = {}) {
            Text(text = "Button 2")
          }
        }
      }
    }
  }
}