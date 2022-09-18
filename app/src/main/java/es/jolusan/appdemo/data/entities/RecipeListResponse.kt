package es.jolusan.appdemo.data.entities

import com.google.gson.annotations.SerializedName

data class RecipeListResponse (
    @SerializedName("recipe")
    val recipe: RecipeResponse
)
