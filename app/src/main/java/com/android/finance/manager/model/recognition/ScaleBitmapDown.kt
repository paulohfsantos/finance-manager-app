package com.android.finance.manager.model.recognition

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

private fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {
  val originalWidth = bitmap.width
  val originalHeight = bitmap.height
  var resizedWidth = maxDimension
  var resizedHeight = maxDimension
  if (originalHeight > originalWidth) {
    resizedHeight = maxDimension
    resizedWidth =
      (resizedHeight * originalWidth.toFloat() / originalHeight.toFloat()).toInt()
  } else if (originalWidth > originalHeight) {
    resizedWidth = maxDimension
    resizedHeight =
      (resizedWidth * originalHeight.toFloat() / originalWidth.toFloat()).toInt()
  } else if (originalHeight == originalWidth) {
    resizedHeight = maxDimension
    resizedWidth = maxDimension
  }
  return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false)
}

//fun scaleBitmapDown(uri: Uri, maxDimension: Int): Bitmap {
//  val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//    ImageDecoder.decodeBitmap(
//      ImageDecoder.createSource(contentResolver.openInputStream(uri))
//    )
//  } else {
//    MediaStore.Images.Media.getBitmap(contentResolver, uri)
//  }
//  return scaleBitmapDown(bitmap, maxDimension)
//}