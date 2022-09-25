package es.jolusan.recipesapp.domain.usecases

import es.jolusan.recipesapp.domain.repositories.RecipeRepository
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(searchWords: String) = repository.getRecipesByWords(searchWords)
}