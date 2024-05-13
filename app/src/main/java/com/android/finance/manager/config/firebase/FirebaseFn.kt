package com.android.finance.manager.config.firebase

import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

private lateinit var functions: FirebaseFunctions

fun initFirebaseFunctions(): FirebaseFunctions {
    functions = Firebase.functions

    return functions
}