package com.skylightdevelopers.android.moviemaasala.core.di

import android.app.Application
import com.skylightdevelopers.android.moviemaasala.app.api.GoogleService
import com.skylightdevelopers.android.moviemaasala.app.util.MConstants
import com.skylightdevelopers.android.moviemaasala.app.util.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object GoogleServiceModule {

    @Singleton
    @Provides
    fun provideGoogleService(application: Application): GoogleService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val oktHttpClient = OkHttpClient.Builder()
                .connectTimeout(MConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(MConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(MConstants.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(NetworkConnectionInterceptor(application.applicationContext))
                .addInterceptor(logging)

        return Retrofit.Builder()
                .baseUrl(MConstants.G_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(oktHttpClient.build())
                .build()
                .create(GoogleService::class.java)
    }
}