package es.jolusan.appdemo.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import es.jolusan.appdemo.R
import es.jolusan.appdemo.databinding.DetailFragmentBinding
import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.utils.ResponseStatus
import es.jolusan.appdemo.utils.loadUrl
import es.jolusan.appdemo.utils.upperCaseFirst
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: DetailFragmentBinding

    private var recipe: RecipeDetail? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val args = DetailFragmentArgs.fromBundle(it)
            recipe = args.recipe
        }
        setupUI()
        setupObservers()

        recipe?.id?.let { viewModel.checkIsFavorite(it) }
    }

    private fun setupUI(){
        recipe?.let { recipeDetail ->
            with (binding) {
                recipeImage.loadUrl(recipeDetail.imageURL)
                recipeNameTextView.text = recipeDetail.label
                descriptionTextView.text = recipeDetail.description
                    .upperCaseFirst()
                dishTypeTextView.text = recipeDetail.dishType
                    .joinToString(", ") { it }
                    .upperCaseFirst()
                mealTypeTextView.text = recipeDetail.mealType
                    .joinToString(", ") { it }
                    .upperCaseFirst()
                recipeIngredientesTextView.text = recipeDetail.ingredientLines
                    .joinToString ( "\n" ) { it }
                    .upperCaseFirst()
                caloriesTextView.text = getString(R.string.calories, recipeDetail.calories.toInt())
                sourceUrlTextView.text = getString(R.string.source_url, recipeDetail.sourceURL)
                sourceUrlTextView.setOnClickListener {
                    val openURL = Intent(android.content.Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(recipeDetail.sourceURL)
                    startActivity(openURL)
                }

            }
        } ?: run {
            binding.recipeLayout.visibility = View.GONE
            binding.errorTextView.text = getString(R.string.detail_not_loaded)
            binding.errorTextView.visibility = View.VISIBLE
        }

    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFavoriteRecipe.collect {
                    if (it is ResponseStatus.Success) {
                        binding.favoriteImage.isChecked = it.data == true
                        binding.favoriteImage.setOnCheckedChangeListener { _, isChecked ->
                            manageFavoriteCheck(isChecked)
                        }
                    }
                }
            }
        }
    }

    private fun manageFavoriteCheck(isChecked: Boolean) {
        recipe?.let {
            if (isChecked) {
                viewModel.addFavorite(it)
                val snack = Snackbar.make(requireActivity().findViewById(android.R.id.content),getString(R.string.favorite_added),Snackbar.LENGTH_SHORT)
                snack.show()
            } else {
                viewModel.removeFavorite(it.id)
                val snack = Snackbar.make(requireActivity().findViewById(android.R.id.content),getString(R.string.favorite_removed),Snackbar.LENGTH_SHORT)
                snack.show()
            }
        }
    }
}