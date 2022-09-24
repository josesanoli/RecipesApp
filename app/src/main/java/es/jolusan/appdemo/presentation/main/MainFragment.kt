package es.jolusan.appdemo.presentation.main

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
import es.jolusan.appdemo.databinding.MainFragmentBinding
import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.domain.model.toRecipe
import es.jolusan.appdemo.utils.ResponseStatus
import es.jolusan.appdemo.utils.hideKeyboard
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        recipesAdapter = RecipesAdapter {
            navigateToDetail(viewModel.onRecipeClicked(it))
        }
        binding.recipesRecyclerView.adapter = recipesAdapter

        binding.searchButton.setOnClickListener {

            hideKeyboard()
            binding.infoTextView.visibility = View.INVISIBLE
            viewModel.getRecipesByWords(binding.searchEditText.text.toString())
        }

        binding.progressBar.visibility = View.GONE

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
            binding.infoTextView.text = getString(R.string.results_empty)
            binding.recipesRecyclerView.visibility = View.GONE
        } else {
            val recipes = recipesList.map { recipeDetail -> recipeDetail.toRecipe() }
            recipesAdapter.recipeList = recipes
        }
    }

    private fun navigateToDetail(recipeDetail: RecipeDetail) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(recipeDetail)
        findNavController().navigate(action)
    }
}