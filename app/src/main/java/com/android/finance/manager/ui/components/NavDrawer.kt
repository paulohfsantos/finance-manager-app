package com.android.finance.manager.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
  scaffoldState: BottomSheetScaffoldState,
  scope: CoroutineScope,
  modifier: Modifier = Modifier,
  onItemClick: (String) -> Unit
) {
  val menuItems = listOf(
    "Home"
  )
  LazyColumn(modifier = modifier) {
    // Drawer items
    items(menuItems) { item ->
      DrawerItem(
        menuItem = item,
        modifier = Modifier,
      ) {
        scope.launch {
          scaffoldState.bottomSheetState.hide()
        }
        onItemClick(item)
      }

      // divider
      Spacer(modifier = Modifier.padding(8.dp))

      // signout
      DrawerItem(
        menuItem = "Sign out",
        modifier = Modifier.clickable {
          // Handle sign out
          print("Handle sign out")
        },
        onItemClick = { menuItem ->
          // Handle sign out click
          print("Handle sign out click")
        }
      )
    }
  }
}

data class MenuItem(
  val id: Int,
  val textId: Int,
)
