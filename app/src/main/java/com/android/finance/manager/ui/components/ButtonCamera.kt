package com.android.finance.manager.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ButtonCamera(handleOpenCamera: () -> Unit) {
  IconButton(
    onClick = { handleOpenCamera() },
    modifier = Modifier.size(48.dp),
  ) {
    Icon(
      imageVector = Icons.Default.CheckCircle, // change to camera icon later
      contentDescription = "Camera",
    )
  }
}