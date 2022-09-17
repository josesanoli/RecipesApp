package es.jolusan.appdemo.domain.model

data class RecipeDetail (
    val label: String,
    val description: String,
    val imageURL: String,
    val sourceURL: String,
    val ingredientLines: List<String>,
    val calories: Float,
    val cuisineType: List<String>,
    val dishType: List<String>
)
