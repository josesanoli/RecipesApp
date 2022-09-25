package es.jolusan.recipesapp.app

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.jolusan.recipesapp.data.database.RecipesDao
import es.jolusan.recipesapp.data.entities.RecipesDBEntity
import es.jolusan.recipesapp.utils.Converter

@Database(entities = [RecipesDBEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}