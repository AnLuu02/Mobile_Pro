package com.example.jetpackcomposedemo.handlePayment.Api

import android.util.Log
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.TlsVersion
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


object HttpProvider {
    suspend fun sendPost(URL: String?, formBody: RequestBody?): JSONObject? {
        var data: JSONObject? = null
        try {
            URL?.let { url ->
                formBody?.let {
                    val spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .cipherSuites(
                            TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
                        )
                        .build()
                    val client: OkHttpClient = OkHttpClient.Builder()
                        .connectionSpecs(listOf<ConnectionSpec>(spec))
                        .callTimeout(5000, TimeUnit.MILLISECONDS)
                        .build()
                    val request: Request = Request.Builder()
                        .url(url)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .post(it)
                        .build()
                    val response = client.newCall(request).execute()
                    response.body?.use {it1->
                        if (!response.isSuccessful) {
                            Log.e("BAD_REQUEST", it1.string())
                        } else {
                            data = JSONObject(it1.string())
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return data
    }
}

