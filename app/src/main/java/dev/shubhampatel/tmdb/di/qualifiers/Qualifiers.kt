package dev.shubhampatel.tmdb.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpLoggingInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiInterceptorOkHttpClient