package es.jolusan.recipesapp.data.entities

import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("hits")
    val hits: List<RecipeListResponse>
)
