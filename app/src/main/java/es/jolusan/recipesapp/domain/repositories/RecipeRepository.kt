package es.jolusan.recipesapp.domain.repositories

import es.jolusan.recipesapp.domain.model.RecipeDetail
import es.jolusan.recipesapp.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipesByWords(searchWords: String): Flow<ResponseStatus<List<RecipeDetail>>>
}