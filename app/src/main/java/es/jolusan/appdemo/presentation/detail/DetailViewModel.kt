package es.jolusan.appdemo.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.domain.usecases.InsertFavoriteRecipeUseCase
import es.jolusan.appdemo.domain.usecases.IsFavoriteRecipeUseCase
import es.jolusan.appdemo.domain.usecases.RemoveFavoriteRecipeUseCase
import es.jolusan.appdemo.utils.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val isFavoriteRecipeUseCase: IsFavoriteRecipeUseCase,
    private val insertFavoriteRecipeUseCase: InsertFavoriteRecipeUseCase,
    private val removeFavoriteRecipeUseCase: RemoveFavoriteRecipeUseCase
): ViewModel() {
    private val _isFavoriteRecipe = MutableStateFlow<ResponseStatus<Boolean>>(ResponseStatus.Init())
    val isFavoriteRecipe = _isFavoriteRecipe.asStateFlow()

    fun checkIsFavorite(recipeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isFavoriteRecipeUseCase(recipeId).collect{
                _isFavoriteRecipe.value = it
            }
        }
    }

    fun addFavorite(recipeDetail: RecipeDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteRecipeUseCase(recipeDetail)
        }
    }

    fun removeFavorite(recipeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFavoriteRecipeUseCase(recipeId)
        }
    }
}