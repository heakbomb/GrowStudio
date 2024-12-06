package com.example.xampp.utils

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Response

object NetworkUtils {
    fun sendHttpRequest(
        context: Context,
        url: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val queue: RequestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                onSuccess(response)
            },
            Response.ErrorListener { error ->
                onError(error.toString())
            })

        queue.add(stringRequest)
    }
}
