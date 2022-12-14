package es.jolusan.recipesapp.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.jolusan.recipesapp.databinding.RecipeItemBinding
import es.jolusan.recipesapp.domain.model.Recipe
import es.jolusan.recipesapp.utils.loadUrl

class RecipesAdapter(
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>(){

    var recipeList: List<Recipe> = listOf()

    inner class RecipeViewHolder(
        private val binding: RecipeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) = with(binding) {
            recipeName.text = recipe.label
            recipeDescription.text = recipe.description
            recipeImage.loadUrl(recipe.imageURL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = recipeList.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = with(holder) {
        val recipe = recipeList[position]
        bind(recipe)
        itemView.setOnClickListener { listener(recipe.id) }
    }

    fun setNewSearchRecipeList (recipes: List<Recipe>){
        recipeList = recipes
        notifyDataSetChanged()
    }

}