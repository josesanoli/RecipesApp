package es.jolusan.appdemo.presentation.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import es.jolusan.appdemo.R
import es.jolusan.appdemo.databinding.BookmarksFragmentBinding
import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.domain.model.toRecipe
import es.jolusan.appdemo.presentation.main.MainFragmentDirections
import es.jolusan.appdemo.presentation.main.RecipesAdapter
import es.jolusan.appdemo.utils.ResponseStatus
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private val viewModel: BookmarksViewModel by viewModels()
    private lateinit var binding: BookmarksFragmentBinding
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BookmarksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        viewModel.getRecipesSavedInDatabase()
    }

    private fun setupUI() {
        recipesAdapter = RecipesAdapter {
            navigateToDetail(viewModel.onRecipeClicked(it))
        }
        binding.recipesRecyclerView.adapter = recipesAdapter
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipes.collect {
                    when (it) {
                        is ResponseStatus.Success -> {
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { recipesList -> updateRecipesList(recipesList) }
                            binding.recipesRecyclerView.visibility = View.VISIBLE
                        }
                        is ResponseStatus.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recipesRecyclerView.visibility = View.GONE
                        }
                        is ResponseStatus.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.infoTextView.visibility = View.VISIBLE
                            binding.infoTextView.text = it.messageResource?.let { resource -> getString(resource) } ?: getString(R.string.error_generic)
                        }
                        is ResponseStatus.Init -> {
                            binding.progressBar.visibility = View.GONE
                            binding.infoTextView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun updateRecipesList (recipesList: List<RecipeDetail>) {
        if (recipesList.isEmpty()){
            binding.infoTextView.visibility = View.VISIBLE
            binding.infoTextView.text = getString(R.string.bookmarks_no_recipes_saved)
            binding.recipesRecyclerView.visibility = View.GONE
        } else {
            binding.infoTextView.visibility = View.GONE
            val recipes = recipesList.map { recipeDetail -> recipeDetail.toRecipe() }
            recipesAdapter.recipeList = recipes
        }
    }

    private fun navigateToDetail(recipeDetail: RecipeDetail) {
        val action = BookmarksFragmentDirections.actionBookmarksFragmentToDetailFragment(recipeDetail)
        findNavController().navigate(action)
    }
}