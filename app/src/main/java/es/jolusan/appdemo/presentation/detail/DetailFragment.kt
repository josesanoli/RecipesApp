package es.jolusan.appdemo.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.jolusan.appdemo.databinding.DetailFragmentBinding
import es.jolusan.appdemo.domain.model.RecipeDetail

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
            binding.recipeTitleTextView.text = recipe?.label
        }
    }
}