package com.android.finance.manager.model.recognition

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.gson.JsonElement
import com.android.finance.manager.config.firebase.initFirebaseFunctions
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import org.json.JSONObject

val functions = initFirebaseFunctions()
fun annotateImage(requestJson: String): Task<JsonElement> {
  return functions
    .getHttpsCallable("annotateImage")
    .call(requestJson)
    .continueWith { task ->
      // This continuation runs on either success or failure, but if the task
      // has failed then result will throw an Exception which will be
      // propagated down.
      val result = task.result?.data
      JsonParser.parseString(Gson().toJson(result))
    }
}

fun createJsonRequest() {
  // Create json request to cloud vision
  val request = JsonObject()
  // Add image to request
  val image = JsonObject()
  image.add("content", JsonPrimitive("base64encoded"))
  request.add("image", image)
  // Add features to the request
  val feature = JsonObject()
  feature.add("type", JsonPrimitive("TEXT_DETECTION"))
  // Alternatively, for DOCUMENT_TEXT_DETECTION:
  // feature.add("type", JsonPrimitive("DOCUMENT_TEXT_DETECTION"))
  val features = JsonArray()
  features.add(feature)
  request.add("features", features)

  val imageContext = JsonObject()
  val languageHints = JsonArray()
  languageHints.add("en")
  imageContext.add("languageHints", languageHints)
  request.add("imageContext", imageContext)

  annotateImage(request.toString())
    .addOnCompleteListener { task ->
      if (!task.isSuccessful) {
        val e = task.exception
        Log.e("AnnotateImageError", e!!.message.toString())
      } else {
        val result = task.result
        val annotation = result!!.asJsonArray[0]
          .asJsonObject["fullTextAnnotation"]
          .asJsonObject
        System.out.format("%nComplete annotation:")
        System.out.format("%n%s", annotation["text"].asString)

        loopOverAnnotations(annotation)

        Log.d("AnnotateImageResult", result.toString())
      }
    }
}

fun loopOverAnnotations(annotation: JsonObject) {
  for (page in annotation["pages"].asJsonArray) {
    var pageText = ""

    for (block in page.asJsonObject["blocks"].asJsonArray) {
      var blockText = ""
      for (para in block.asJsonObject["paragraphs"].asJsonArray) {
        var paraText = ""
        for (word in para.asJsonObject["words"].asJsonArray) {
          var wordText = ""
          for (symbol in word.asJsonObject["symbols"].asJsonArray) {
            wordText += symbol.asJsonObject["text"].asString
            System.out.format(
              "Symbol text: %s (confidence: %f)%n",
              symbol.asJsonObject["text"].asString,
              symbol.asJsonObject["confidence"].asFloat,
            )
          }
          System.out.format(
            "Word text: %s (confidence: %f)%n%n",
            wordText,
            word.asJsonObject["confidence"].asFloat,
          )
          System.out.format("Word bounding box: %s%n", word.asJsonObject["boundingBox"])
          paraText = String.format("%s%s ", paraText, wordText)
        }
        System.out.format("%nParagraph: %n%s%n", paraText)
        System.out.format("Paragraph bounding box: %s%n", para.asJsonObject["boundingBox"])
        System.out.format("Paragraph Confidence: %f%n", para.asJsonObject["confidence"].asFloat)
        blockText += paraText
      }
      pageText += blockText
    }
  }
}