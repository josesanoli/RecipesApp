package es.jolusan.appdemo.domain.usecases

import es.jolusan.appdemo.domain.repositories.RecipeRepository
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(searchWords: String) = repository.getRecipesByWords(searchWords)
}