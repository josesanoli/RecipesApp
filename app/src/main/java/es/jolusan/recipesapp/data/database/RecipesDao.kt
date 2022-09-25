package es.jolusan.recipesapp.data.database

import androidx.room.*
import es.jolusan.recipesapp.data.entities.RecipesDBEntity

@Dao
interface RecipesDao {
    @Query("SELECT * FROM favorite_recipes_database")
    fun getAll(): List<RecipesDBEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipesDBEntity)

    @Query("DELETE FROM favorite_recipes_database WHERE id = :recipeId")
    suspend fun removeFavorite(recipeId: String)

    @Query("SELECT * FROM favorite_recipes_database WHERE id LIKE :recipeId")
    fun findByName(recipeId: String): RecipesDBEntity?
}