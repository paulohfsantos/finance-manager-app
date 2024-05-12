package com.android.finance.manager.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import com.android.finance.manager.model.Document

@Composable
fun DocumentItem(
  document: Document,
  onItemClick: (Document) -> Unit,
  editDocument: (Document) -> Unit,
  deleteDocument: (Document) -> Unit
) {
  var isCtxMenuVisible by rememberSaveable { mutableStateOf(false) }
  var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
  var itemHeight by remember { mutableStateOf(0.dp) }
  val density = LocalDensity.current
  val interactionSrc = remember { MutableInteractionSource() }

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .indication(interactionSource = interactionSrc, indication = LocalIndication.current)
      .padding(8.dp)
      .onSizeChanged {
        itemHeight = with(density) { it.height.toDp() }
      }
      .clickable { onItemClick(document) }
      .pointerInput(true) {
        detectTapGestures(
          onLongPress = {
            isCtxMenuVisible = true
            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
          },
          onPress = {
            val press = PressInteraction.Press(it)
            interactionSrc.emit(press)
            tryAwaitRelease()
            interactionSrc.emit(PressInteraction.Release(press))
          }
        )
      }
  ) {
    Column {
      ListItem(
        headlineContent = {
          Text(text = document.name, style = MaterialTheme.typography.bodyLarge)
        },
      )
    }

    DropdownMenu(
      expanded = isCtxMenuVisible,
      onDismissRequest = { isCtxMenuVisible = false },
      offset = pressOffset.copy(
        y = pressOffset.y - itemHeight,
      )
    ) {
      DropdownMenuItem(
        text = { Text("Editar") },
        onClick = { editDocument(document) }
      )
      DropdownMenuItem(
        text = { Text("Excluir") },
        onClick = { deleteDocument(document) }
      )
    }
  }
}
