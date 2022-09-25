package es.jolusan.appdemo.domain.usecases

import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.domain.repositories.FavoriteRecipesRepository
import javax.inject.Inject

class InsertFavoriteRecipeUseCase @Inject constructor(
    private val repository: FavoriteRecipesRepository
) {
    suspend operator fun invoke(recipe: RecipeDetail) = repository.insertRecipeInDB(recipe)
}