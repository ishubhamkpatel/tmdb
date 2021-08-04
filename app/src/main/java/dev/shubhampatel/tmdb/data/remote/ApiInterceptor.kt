package dev.shubhampatel.tmdb.data.remote

import dev.shubhampatel.tmdb.utility.AppSecrets
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {

    companion object {
        private const val HEADER_CONTENT_TYPE = "Content-Type"
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val QUERY_API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request()
        val newHttpUrl = currentRequest.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, AppSecrets.getApiKey())
            .build()
        val newRequest = currentRequest.newBuilder().url(newHttpUrl)
            .addHeader(HEADER_CONTENT_TYPE, "application/json;charset=utf-8")
            .addHeader(HEADER_AUTHORIZATION, AppSecrets.getAuthToken())
            .build()
        return chain.proceed(newRequest)
    }
}