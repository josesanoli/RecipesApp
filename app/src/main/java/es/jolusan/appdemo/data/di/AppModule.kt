package es.jolusan.appdemo.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.jolusan.appdemo.BuildConfig
import es.jolusan.appdemo.app.AppDatabase
import es.jolusan.appdemo.data.apiservice.RecipeApi
import es.jolusan.appdemo.data.database.RecipesDao
import es.jolusan.appdemo.data.repo.FavoriteRecipesRepositoryImpl
import es.jolusan.appdemo.data.repo.RecipeRepositoryImpl
import es.jolusan.appdemo.domain.repositories.FavoriteRecipesRepository
import es.jolusan.appdemo.domain.repositories.RecipeRepository
import es.jolusan.appdemo.utils.Constants
import es.jolusan.appdemo.utils.Converter
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val converter: Converter = Converter()

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

    @Provides
    @Singleton
    fun provideFavoriteRecipesRepository(localDB: RecipesDao): FavoriteRecipesRepository {
        return FavoriteRecipesRepositoryImpl(localDB)
    }

    @Provides
    fun provideRecipesDao(appDatabase: AppDatabase): RecipesDao {
        return appDatabase.recipesDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "favorite_recipes_database"
            )
            .addTypeConverter(converter)
            .build()
    }


}