package ru.alexeyoss.foodie.services.network.di

import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexeyoss.foodie.core.common.di.scope.PerApplication
import ru.alexeyoss.foodie.services.network.BuildConfig
import ru.alexeyoss.foodie.services.network.MainApiService
import timber.log.Timber

interface NetworkProvider {
    fun provideApiService(): MainApiService
}

@[
PerApplication Component(
    modules = [NetworkModule::class]
)
]
interface NetworkComponent : NetworkProvider

@Module
internal object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Timber.i(message) }
            .also { it.level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL + BuildConfig.API_VERSION)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): MainApiService =
        retrofit.create(MainApiService::class.java)
}
