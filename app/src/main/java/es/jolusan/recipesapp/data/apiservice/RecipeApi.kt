package es.jolusan.recipesapp.data.apiservice

import es.jolusan.recipesapp.data.entities.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("api/recipes/v2")
    suspend fun getRecipesByWords(
        @Query ("q") searchWords: String
    ): SearchResponse
}