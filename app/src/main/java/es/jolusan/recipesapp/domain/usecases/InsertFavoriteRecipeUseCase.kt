package es.jolusan.recipesapp.domain.usecases

import es.jolusan.recipesapp.domain.model.RecipeDetail
import es.jolusan.recipesapp.domain.repositories.FavoriteRecipesRepository
import javax.inject.Inject

class InsertFavoriteRecipeUseCase @Inject constructor(
    private val repository: FavoriteRecipesRepository
) {
    suspend operator fun invoke(recipe: RecipeDetail) = repository.insertRecipeInDB(recipe)
}