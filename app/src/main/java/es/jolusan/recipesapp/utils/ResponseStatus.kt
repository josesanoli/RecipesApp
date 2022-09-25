package es.jolusan.recipesapp.utils

sealed class ResponseStatus <T>(val data: T? = null, val messageResource: Int? = null) {

    class Success<T>(data: T) : ResponseStatus<T>(data)

    class Error<T>(messageResource: Int, data: T? = null) : ResponseStatus<T>(data, messageResource)

    class Loading<T>() : ResponseStatus<T>()

    class Init<T>() : ResponseStatus<T>()
}