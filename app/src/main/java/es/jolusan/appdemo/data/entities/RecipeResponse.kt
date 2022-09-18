package es.jolusan.appdemo.data.entities

import com.google.gson.annotations.SerializedName
import es.jolusan.appdemo.domain.model.RecipeDetail

data class RecipeResponse (
    @SerializedName("uri")
    val uri: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("image")
    val imageURL: String,
    @SerializedName("url")
    val sourceURL: String,
    @SerializedName("ingredientLines")
    val ingredientLines: List<String>,
    @SerializedName("calories")
    val calories: Float,
    @SerializedName("cuisineType")
    val cuisineType: List<String>,
    @SerializedName("dishType")
    val dishType: List<String>?
)

fun RecipeResponse.toRecipeDetail(): RecipeDetail = RecipeDetail(
    id = uri,
    label = label,
    description = cuisineType.joinToString(", ") { it },
    imageURL = imageURL,
    sourceURL = sourceURL,
    ingredientLines = ingredientLines,
    calories = calories,
    dishType = dishType ?: listOf()
)

