package es.jolusan.appdemo.domain.repositories

import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow

interface FavoriteRecipesRepository {
    suspend fun getAllFavoriteRecipes(): Flow<ResponseStatus<List<RecipeDetail>>>
    suspend fun insertRecipeInDB(recipe: RecipeDetail)
    suspend fun removeRecipeFromDB(recipeId: String)
    suspend fun isFavoriteRecipe(recipeId: String): Flow<ResponseStatus<Boolean>>
}