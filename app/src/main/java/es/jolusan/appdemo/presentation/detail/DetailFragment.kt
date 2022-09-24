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
import dagger.hilt.android.AndroidEntryPoint
import es.jolusan.appdemo.R
import es.jolusan.appdemo.databinding.DetailFragmentBinding
import es.jolusan.appdemo.domain.model.RecipeDetail
import es.jolusan.appdemo.utils.loadUrl
import es.jolusan.appdemo.utils.upperCaseFirst

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
}