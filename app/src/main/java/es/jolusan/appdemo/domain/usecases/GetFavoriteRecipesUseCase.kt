package es.jolusan.appdemo.domain.usecases

import es.jolusan.appdemo.domain.repositories.FavoriteRecipesRepository
import javax.inject.Inject

class GetFavoriteRecipesUseCase @Inject constructor(
    private val repository: FavoriteRecipesRepository
) {
    suspend operator fun invoke() = repository.getAllFavoriteRecipes()
}