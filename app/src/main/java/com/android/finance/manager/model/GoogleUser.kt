package com.android.finance.manager.model

data class GoogleUser(
  val sub: String?,
  val email: String?,
  val emailVerified: Boolean?,
  val fullName: String?,
  val givenName: String?,
  val familyName: String?,
  val picture: String?,
  val issuedAt: Long?,
  val expirationTime: Long?,
  val locale: String?
)