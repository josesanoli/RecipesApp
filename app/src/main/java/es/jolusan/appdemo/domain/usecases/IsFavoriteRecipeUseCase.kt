package es.jolusan.appdemo.domain.usecases

import es.jolusan.appdemo.domain.repositories.FavoriteRecipesRepository
import javax.inject.Inject

class IsFavoriteRecipeUseCase @Inject constructor(
    private val repository: FavoriteRecipesRepository
) {
    suspend operator fun invoke(recipeId: String) = repository.isFavoriteRecipe(recipeId)
}