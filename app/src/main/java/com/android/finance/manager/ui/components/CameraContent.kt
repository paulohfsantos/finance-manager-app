package com.android.finance.manager.ui.components

import android.content.Context
import android.view.ViewGroup
import android.graphics.Color
import android.widget.LinearLayout
import androidx.camera.core.AspectRatio
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.android.finance.manager.model.recognition.TextRecognitionAnalyzer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraContent() {
  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current
  val cameraController: LifecycleCameraController = remember {
    LifecycleCameraController(context)
  }
  var detectedText by remember { mutableStateOf("No text detected yet...") }

  fun onTextUpdated(text: String) {
    detectedText = text
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = { TopAppBar(title = { Text("Text Scanner") }) },
  ) { paddingValues: PaddingValues ->
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = androidx.compose.ui.Alignment.BottomCenter
    ) {
      AndroidView(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues),
        factory = { context ->
          PreviewView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
              ViewGroup.LayoutParams.MATCH_PARENT,
              ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.BLACK)
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = PreviewView.ScaleType.FILL_START
          }.also { previewView ->
            startTextRecognition(
              context = context,
              cameraController = cameraController,
              lifecycleOwner = lifecycleOwner,
              previewView = previewView,
              onDetectedTextUpdated = ::onTextUpdated
            )
          }
        }
      )

      Text(
        modifier = Modifier
          .fillMaxWidth()
          .background(androidx.compose.ui.graphics.Color.White)
          .padding(16.dp),
        text = detectedText,
      )
    }
  }
}

fun startTextRecognition(
  context: Context,
  cameraController: LifecycleCameraController,
  lifecycleOwner: LifecycleOwner,
  previewView: PreviewView,
  onDetectedTextUpdated: (String) -> Unit
) {

  cameraController.imageAnalysisTargetSize = CameraController.OutputSize(AspectRatio.RATIO_16_9)
  cameraController.setImageAnalysisAnalyzer(
    ContextCompat.getMainExecutor(context),
    TextRecognitionAnalyzer(onDetectedTextUpdated = onDetectedTextUpdated)
  )

  cameraController.bindToLifecycle(lifecycleOwner)
  previewView.controller = cameraController
}