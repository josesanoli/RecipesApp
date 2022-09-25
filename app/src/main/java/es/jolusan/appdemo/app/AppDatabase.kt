package es.jolusan.appdemo.app

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.jolusan.appdemo.data.database.RecipesDao
import es.jolusan.appdemo.data.entities.RecipesDBEntity
import es.jolusan.appdemo.utils.Converter

@Database(entities = [RecipesDBEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}