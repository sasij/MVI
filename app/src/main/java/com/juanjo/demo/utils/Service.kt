package com.juanjo.demo.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import io.reactivex.Single

class Service(
  val httpClient: OkHttpClient = OkHttpClient(),
  val parser: Gson = Gson()
) {
  val type = object : TypeToken<Map<String, Any>>() {}.type

  fun get(): Single<Map<String, String>> {
    return Single.fromCallable {
      httpClient.newCall(Request.Builder().url(URL).build()).execute()
    }.map { response ->
      val result = parser.fromJson(response.body().string(), type) as Map<String, Any>
      mapOf(
        Pair("USD", getValue("USD", result)),
        Pair("GBP", getValue("GBP", result)),
        Pair("EUR", getValue("EUR", result))
      )
    }
  }

  private fun getValue(currency: String, response: Map<String, Any>): String {
    val values = (response["bpi"] as Map<String, Any>)
    return (values[currency] as Map<String, Any>)["rate_float"].toString()
  }

  companion object {
    const val URL = "https://api.coindesk.com/v1/bpi/currentprice.json"
  }

}