package es.jolusan.recipesapp.domain.usecases

import es.jolusan.recipesapp.domain.repositories.FavoriteRecipesRepository
import javax.inject.Inject

class RemoveFavoriteRecipeUseCase @Inject constructor(
    private val repository: FavoriteRecipesRepository
) {
    suspend operator fun invoke(recipeId: String) = repository.removeRecipeFromDB(recipeId)
}