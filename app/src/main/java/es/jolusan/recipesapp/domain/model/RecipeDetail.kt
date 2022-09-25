package es.jolusan.recipesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDetail (
    val id: String,
    val label: String,
    val description: String,
    val imageURL: String,
    val sourceURL: String,
    val ingredientLines: List<String>,
    val calories: Float,
    val mealType: List<String>,
    val dishType: List<String>
) : Parcelable
