package es.jolusan.appdemo.domain.model

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
    val dishType: List<String>
) : Parcelable
