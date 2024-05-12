package com.android.finance.manager.config.interfaces

data class SignInState(
  val isSignInSuccessful: Boolean = false,
  val signInError: String? = null
)