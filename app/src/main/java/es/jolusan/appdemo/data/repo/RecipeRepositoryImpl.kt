package es.jolusan.appdemo.data.repo

import es.jolusan.appdemo.R
import es.jolusan.appdemo.data.apiservice.RecipeApi
import es.jolusan.appdemo.data.entities.toRecipeDetail
import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.domain.repositories.RecipeRepository
import es.jolusan.appdemo.utils.Constants
import es.jolusan.appdemo.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(private val api: RecipeApi) : RecipeRepository {
    override suspend fun getRecipesByWords(searchWords: String): Flow<ResponseStatus<List<RecipeDetail>>> = flow {
        try {
            emit(ResponseStatus.Loading())
            val list = api.getRecipesByWords(
                searchWords
            ).hits.map { it.recipe.toRecipeDetail() }
            emit(ResponseStatus.Success(list))
        } catch (e: HttpException) {
            emit(ResponseStatus.Error((R.string.error_generic)))
        } catch (e: IOException) {
            emit(ResponseStatus.Error((R.string.error_generic)))
        }
    }
}