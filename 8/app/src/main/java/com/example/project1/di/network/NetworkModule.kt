package com.example.project1.di.network

import com.example.project1.network.api.JokesApi
import com.example.project1.network.repository.JokesRepository
import com.example.project1.network.repository.JokesRepositoryImpl
import com.example.project1.utilities.AppConstants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single(named("base_url")) { BaseUrlProvider() }
    single { HttpLoggingInterceptorProvider() }
    single { StethoInterceptorProvider() }
    single { AuthInterceptorProvider(apiKey = "") }
    single {
        OkHttpProvider(
            loggingInterceptor = get(),
            stethoInterceptor = get(),
            authInterceptor = get()
        )
    }
    single { CallAdapterFactoryProvider() }
    single { ConverterFactoryProvider() }
    single {
        RetrofitProvider(
            baseUrl = get(named("base_url")),
            okHttpClient = get(),
            gsonConverterFactory = get(),
            callAdapterFactory = get()
        )
    }
    single { JokesApiProvider(retrofit = get()) }
}

val repositoryModule = module {
    single { JokesRepositoryProvider(jokesApi = get()) }
}
fun AuthInterceptorProvider(apiKey: String): Interceptor {
    return Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
}
fun JokesApiProvider(retrofit: Retrofit): JokesApi = retrofit.create(JokesApi::class.java)

fun BaseUrlProvider(): String = AppConstants.BASE_URL

fun HttpLoggingInterceptorProvider(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(
    ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun StethoInterceptorProvider(): StethoInterceptor = StethoInterceptor()

fun OkHttpProvider(
    loggingInterceptor: HttpLoggingInterceptor,
    stethoInterceptor: StethoInterceptor,
    authInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(stethoInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()
}

fun CallAdapterFactoryProvider(): CallAdapter.Factory = CoroutineCallAdapterFactory()

fun ConverterFactoryProvider(): Converter.Factory = GsonConverterFactory.create()

fun RetrofitProvider(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    gsonConverterFactory: Converter.Factory,
    callAdapterFactory: CallAdapter.Factory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()
}

fun JokesRepositoryProvider(jokesApi: JokesApi): JokesRepository = JokesRepositoryImpl(jokesApi)
