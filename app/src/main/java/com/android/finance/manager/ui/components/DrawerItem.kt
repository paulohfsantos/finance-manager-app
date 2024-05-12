package com.android.finance.manager.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerItem(
  menuItem: String,
  modifier: Modifier = Modifier,
  onItemClick: (String) -> Unit
) {
  Column(
    modifier = Modifier.clickable {
      onItemClick(menuItem)
    }
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = modifier
        .padding(8.dp)
    ) {
      Text(
        text = menuItem,
        fontSize = 25.sp,
        modifier = Modifier.padding(horizontal = 10.dp)
      )
    }
    Spacer(modifier = Modifier.padding(8.dp))
  }
}