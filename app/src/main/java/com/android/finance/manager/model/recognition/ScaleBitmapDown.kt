package com.android.finance.manager.model.recognition

import android.graphics.Bitmap

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