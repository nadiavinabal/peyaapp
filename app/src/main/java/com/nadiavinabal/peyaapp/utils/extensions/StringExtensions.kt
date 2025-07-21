package com.nadiavinabal.peyaapp.utils.extensions

import java.util.regex.Pattern

private fun String.isValidEmail(): Boolean {
    return Pattern.matches(
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$",
        this
    )
}