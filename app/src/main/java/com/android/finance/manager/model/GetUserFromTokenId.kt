package com.android.finance.manager.model

import android.util.Log
import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT

fun getUserFromTokenId(tokenId: String): GoogleUser? {
  Log.d("OneTapCompose", "Trying to get user from token: $tokenId")
  try {
    val jwt = JWT(tokenId)
    return GoogleUser(
      sub = jwt.claims[Claims.SUB]?.asString(),
      email = jwt.claims[Claims.EMAIL]?.asString(),
      emailVerified = jwt.claims[Claims.EMAIL_VERIFIED]?.asBoolean(),
      fullName = jwt.claims[Claims.FUll_NAME]?.asString(),
      givenName = jwt.claims[Claims.GIVEN_NAME]?.asString(),
      familyName = jwt.claims[Claims.FAMILY_NAME]?.asString(),
      picture = jwt.claims[Claims.PICTURE]?.asString(),
      issuedAt = jwt.claims[Claims.ISSUED_AT]?.asLong(),
      expirationTime = jwt.claims[Claims.EXPIRATION_TIME]?.asLong(),
      locale = jwt.claims[Claims.LOCALE]?.asString()
    )
  } catch (e: Exception) {
    Log.e("OneTapCompose", e.toString())
    return null
  } catch (e: DecodeException) {
    Log.e("OneTapCompose", e.toString())
    return null
  }
}