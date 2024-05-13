package com.android.finance.manager.view.pages

import CameraScreen
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.finance.manager.model.Document
import com.android.finance.manager.model.GoogleUser
import com.android.finance.manager.ui.components.AppBar
import com.android.finance.manager.ui.components.DocumentItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

// Mock data for preview
// Mock data for preview
val documentList = listOf(
  Document(1, "Documento 1", "caminho_da_imagem_1"),
  Document(2, "Documento 2", "caminho_da_imagem_2"),
  Document(3, "Documento 3", "caminho_da_imagem_3")
)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun IndexScreen(
  documents: List<Document>,
  onItemClick: (Document) -> Unit,
  navigate: NavController,
  user: GoogleUser? = null
) {
  val cameraPermissionState = rememberPermissionState(
    android.Manifest.permission.CAMERA
  )
  val onRequestPermission = cameraPermissionState::launchPermissionRequest
  val ctx = LocalContext.current

  // load CameraScreen after clicking the Button
  if (cameraPermissionState.status.isGranted) {
    Log.i("IndexScreen", "Camera permission granted")

    LaunchedEffect(Unit) {
      Log.i("IndexScreen", "caiu aqui")
    }
  }

  if (cameraPermissionState.status.shouldShowRationale) {
    Log.i("IndexScreen", "Camera permission should show rationale")
    Toast.makeText(ctx, "Precisamos da permissão de câmera", Toast.LENGTH_SHORT).show()
  }

  if (!cameraPermissionState.status.isGranted) {
    Log.i("IndexScreen", "Camera permission denied")
    Toast.makeText(ctx, "Permissão de câmera negada", Toast.LENGTH_SHORT).show()
  }

  LaunchedEffect(Unit) {
    Log.i("IndexScreen", "caiu aqui no final")
  }

  Surface(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      AppBar(
        title = "Documentos",
        navController = navigate
      )
      LazyColumn {
        items(documents) { document ->
          DocumentItem(
            document = document,
            onItemClick = onItemClick,
            editDocument = { editDocument(document) },
            deleteDocument = { deleteDocument(document) }
          )
        }
      }

      FloatingActionButton(
        onClick = {
          onRequestPermission()
        },
        modifier = Modifier
          .padding(16.dp)
          .align(Alignment.End)
      ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
      }
    }
  }
}

fun deleteDocument(document: Document) {
  // Delete document
  print("Delete document")
}

fun editDocument(document: Document) {
  // Edit document
  print("Edit document")
}