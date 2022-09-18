package es.jolusan.appdemo.domain.repositories

import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipesByWords(searchWords: String): Flow<ResponseStatus<List<RecipeDetail>>>
}