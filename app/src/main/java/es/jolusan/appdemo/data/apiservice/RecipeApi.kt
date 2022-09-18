package es.jolusan.appdemo.data.apiservice

import es.jolusan.appdemo.data.entities.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("api/recipes/v2")
    suspend fun getRecipesByWords(
        @Query ("q") searchWords: String
    ): SearchResponse
}