package com.example.annguyen.kikichat.extensions

import java.util.regex.Matcher
import java.util.regex.Pattern


val VALID_EMAIL_ADDRESS_REGEX: Pattern  =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
fun String.isCorrectEmail():Boolean{
    val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(this)
    return matcher.find()
}