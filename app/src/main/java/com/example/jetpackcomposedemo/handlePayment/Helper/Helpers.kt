package com.example.jetpackcomposedemo.handlePayment.Helper

import android.annotation.SuppressLint
import com.example.jetpackcomposedemo.handlePayment.Helper.HMAC.HMacUtil
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects


object Helpers {
    private var transIdDefault = 1

    @get:SuppressLint("DefaultLocale")
    val appTransId: String
        get() {
            if (transIdDefault >= 100000) {
                transIdDefault = 1
            }
            transIdDefault += 1
            @SuppressLint("SimpleDateFormat") val formatDateTime = SimpleDateFormat("yyMMdd_hhmmss")
            val timeString = formatDateTime.format(Date())
            return String.format("%s%06d", timeString, transIdDefault)
        }

    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
    fun getMac(key: String, data: String): String {
        return Objects.requireNonNull(HMacUtil.HMacHexStringEncode(HMacUtil.HMACSHA256, key, data)).toString()
    }
}



