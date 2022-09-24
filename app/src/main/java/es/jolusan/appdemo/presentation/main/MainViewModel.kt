package es.jolusan.appdemo.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.domain.usecases.GetRecipesUseCase
import es.jolusan.appdemo.utils.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase
): ViewModel() {
    private val _recipes = MutableStateFlow<ResponseStatus<List<RecipeDetail>>>(ResponseStatus.Init())
    val recipes = _recipes.asStateFlow()

    fun getRecipesByWords(searchWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getRecipesUseCase(searchWord).collect {
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