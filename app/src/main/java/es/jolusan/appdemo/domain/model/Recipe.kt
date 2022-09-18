package es.jolusan.appdemo.domain.model

data class Recipe (
    val id: String,
    val label: String,
    val description: String,
    val imageURL: String
)

fun RecipeDetail.toRecipe() = Recipe (
    id = id,
    label = label,
    description = description,
    imageURL = imageURL
)