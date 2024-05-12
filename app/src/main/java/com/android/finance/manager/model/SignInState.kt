package com.android.finance.manager.model

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf

@Stable
class SignInState(open: Boolean = false) {
  var opened by mutableStateOf(open)
    private set

  fun open() {
    Log.d("SignInState", "Opening One-Tap dialog")
    opened = true
  }

  internal fun close() {
    opened = false
  }
}