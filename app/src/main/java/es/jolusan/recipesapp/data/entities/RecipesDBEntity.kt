package es.jolusan.recipesapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.jolusan.recipesapp.domain.model.RecipeDetail

@Entity(tableName = "favorite_recipes_database")
data class RecipesDBEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image_url") val imageURL: String,
    @ColumnInfo(name = "source_url") val sourceURL: String,
    @ColumnInfo(name = "ingredient_lines") val ingredientLines: List<String>,
    @ColumnInfo(name = "calories") val calories: Float,
    @ColumnInfo(name = "meal_type") val mealType: List<String>,
    @ColumnInfo(name = "dish_type") val dishType: List<String>
)

fun RecipesDBEntity.toRecipeDetail() : RecipeDetail = RecipeDetail(
    id = id,
    label = label,
    description = description,
    imageURL = imageURL,
    sourceURL = sourceURL,
    ingredientLines = ingredientLines,
    calories = calories,
    mealType = mealType,
    dishType = dishType
)