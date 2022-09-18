package es.jolusan.appdemo.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.jolusan.appdemo.BuildConfig
import es.jolusan.appdemo.data.apiservice.RecipeApi
import es.jolusan.appdemo.data.repo.RecipeRepositoryImpl
import es.jolusan.appdemo.domain.repositories.RecipeRepository
import es.jolusan.appdemo.utils.Constants
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            addInterceptor { chain ->
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("type", Constants.API_TYPE,)
                    .addQueryParameter("app_id", BuildConfig.APP_ID,)
                    .addQueryParameter("app_key", BuildConfig.APP_KEY)
                    .build()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(api: RecipeApi): RecipeRepository {
        return RecipeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRecipeApi(okHttpClient: OkHttpClient): RecipeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RecipeApi::class.java)
    }
}