package dev.shubhampatel.tmdb.utility

object AppSecrets {

    init {
        System.loadLibrary("api-keys")
    }

    external fun getAuthToken(): String
    external fun getApiKey(): String
}