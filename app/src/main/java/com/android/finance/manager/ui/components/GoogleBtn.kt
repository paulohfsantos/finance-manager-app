package com.android.finance.manager.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.finance.manager.model.SignInState
import com.android.finance.manager.R

@Composable
fun GoogleBtn(
  clientId: String,
  state: SignInState,
  onTokenIdReceived: ((String) -> Unit)? = null,
  onDialogDismissed: ((String) -> Unit)? = null,
  iconOnly: Boolean = false,
  theme: GoogleButtonTheme = if (isSystemInDarkTheme()) GoogleButtonTheme.Dark
  else GoogleButtonTheme.Light,
  colors: ButtonColors = ButtonDefaults.buttonColors(
    containerColor = when (theme) {
      GoogleButtonTheme.Light -> Color.White
      GoogleButtonTheme.Dark -> Color(0xFF131314)
      GoogleButtonTheme.Neutral -> Color(0xFFF2F2F2)
    },
    contentColor = when (theme) {
      GoogleButtonTheme.Dark -> Color(0xFFE3E3E3)
      else -> Color(0xFF1F1F1F)
    },
  ),
  border: BorderStroke? = when (theme) {
    GoogleButtonTheme.Light -> BorderStroke(
      width = 1.dp,
      color = Color(0xFF747775),
    )

    GoogleButtonTheme.Dark -> BorderStroke(
      width = 1.dp,
      color = Color(0xFF8E918F),
    )

    GoogleButtonTheme.Neutral -> null
  },
  shape: Shape = ButtonDefaults.shape,
  onClick: (() -> Unit)? = null,
) {
  SignInPopup(
    state = state,
    clientId = clientId,
    onTokenIdReceived = { tokenId ->
      onTokenIdReceived?.invoke(tokenId)
    },
    onDialogDismissed = { message ->
      onDialogDismissed?.invoke(message)
    }
  )

  Button(
    modifier = Modifier.width(if (iconOnly) 40.dp else Dp.Unspecified),
    onClick = {
      state.open()
      onClick?.invoke()
    },
    shape = shape,
    colors = colors,
    contentPadding = PaddingValues(horizontal = if (iconOnly) 9.5.dp else 12.dp),
    border = border,
  ) {
    Box(
      modifier = Modifier
        .padding(end = if (iconOnly) 0.dp else 10.dp)
        .paint(painter = painterResource(id = R.drawable.google_logo))
    )
    if (!iconOnly) {
      Text(
        text = "Sign in with Google",
        maxLines = 1,
      )
    }
  }
}

enum class GoogleButtonTheme { Light, Dark, Neutral }