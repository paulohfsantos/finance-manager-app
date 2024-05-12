package com.android.finance.manager.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.android.finance.manager.model.GoogleUser

@Composable
fun AppMenu(
  user: GoogleUser? = null,
  signOut: () -> Unit = {},
) {
  var expanded by rememberSaveable { mutableStateOf(false) }
}