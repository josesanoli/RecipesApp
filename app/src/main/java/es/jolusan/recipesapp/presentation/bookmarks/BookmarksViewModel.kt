package es.jolusan.recipesapp.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jolusan.recipesapp.domain.model.RecipeDetail
import es.jolusan.recipesapp.domain.usecases.GetFavoriteRecipesUseCase
import es.jolusan.recipesapp.utils.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase
): ViewModel() {
    private val _recipes = MutableStateFlow<ResponseStatus<List<RecipeDetail>>>(ResponseStatus.Init())
    val recipes = _recipes.asStateFlow()

    fun getRecipesSavedInDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteRecipesUseCase().collect {
                _recipes.value = it
            }
        }
    }

    fun onRecipeClicked(recipeId: String): RecipeDetail {
        _recipes.value.data!!.first { it.id == recipeId }.let { recipeDetail ->
            return recipeDetail
        }
    }
}