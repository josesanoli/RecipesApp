package es.jolusan.appdemo.data.repo

import es.jolusan.appdemo.data.database.RecipesDao
import es.jolusan.appdemo.data.entities.RecipesDBEntity
import es.jolusan.appdemo.data.entities.toRecipeDetail
import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.domain.repositories.FavoriteRecipesRepository
import es.jolusan.appdemo.utils.ResponseStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRecipesRepositoryImpl  @Inject constructor(
    private val localDB: RecipesDao
    ) : FavoriteRecipesRepository {

    override suspend fun getAllFavoriteRecipes(): Flow<ResponseStatus<List<RecipeDetail>>> = flow {
        emit(ResponseStatus.Loading())
        val list : List<RecipeDetail> = localDB.getAll().map { it.toRecipeDetail() }
        emit(ResponseStatus.Success(list))
    }

    override suspend fun insertRecipeInDB(recipe: RecipeDetail) {
        val dbRecipe = RecipesDBEntity(
            id = recipe.id,
            label = recipe.label,
            description = recipe.description,
            imageURL = recipe.imageURL,
            sourceURL = recipe.sourceURL,
            ingredientLines = recipe.ingredientLines,
            calories = recipe.calories,
            mealType = recipe.mealType,
            dishType = recipe.dishType
        )
        localDB.insert(dbRecipe)
    }

    override suspend fun removeRecipeFromDB(recipeId: String) {
        localDB.removeFavorite(recipeId)
    }

    override suspend fun isFavoriteRecipe(recipeId: String): Flow<ResponseStatus<Boolean>> = flow {
        val isFavorite = localDB.findByName(recipeId) != null
        emit(ResponseStatus.Success(isFavorite))
    }

}