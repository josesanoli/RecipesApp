package es.jolusan.appdemo.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converter {
    private val gson = Gson()
    private val listTypeConverter = object : TypeToken<List<String>>() {}.type

    @TypeConverter
    fun fromListToJson(list: List<String>): String = gson.toJson(list, listTypeConverter)

    @TypeConverter
    fun fromJsonToList(icon: String): List<String> = gson.fromJson(icon, listTypeConverter)
}