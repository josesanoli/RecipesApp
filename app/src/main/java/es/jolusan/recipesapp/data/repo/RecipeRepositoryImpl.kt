package es.jolusan.recipesapp.data.repo

import es.jolusan.recipesapp.R
import es.jolusan.recipesapp.data.apiservice.RecipeApi
import es.jolusan.recipesapp.data.entities.toRecipeDetail
import es.jolusan.recipesapp.domain.model.RecipeDetail
import es.jolusan.recipesapp.domain.repositories.RecipeRepository
import es.jolusan.recipesapp.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(private val api: RecipeApi) : RecipeRepository {
    override suspend fun getRecipesByWords(searchWords: String): Flow<ResponseStatus<List<RecipeDetail>>> = flow {
        try {
            emit(ResponseStatus.Loading())
            val list = api.getRecipesByWords(
                searchWords
            ).hits.map { it.recipe.toRecipeDetail() }
            emit(ResponseStatus.Success(list))
        } catch (e: Exception) {
            emit(ResponseStatus.Error((handleError(e))))
        }
    }

    private fun handleError(throwable: Throwable): Int {
        return when (throwable) {
            is HttpException -> when (throwable.code()) {
                401 -> R.string.error_api_key
                else -> R.string.error_generic
            }
            is IOException -> R.string.error_connection
            else -> R.string.error_generic
        }
    }
}