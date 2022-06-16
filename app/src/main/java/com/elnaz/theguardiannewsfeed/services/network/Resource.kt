package com.elnaz.theguardiannewsfeed.services.network

import com.elnaz.theguardiannewsfeed.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    companion object {

        fun <T> success(data: T?): Resource<T> =
            Resource(status = Status.SUCCESS, data = data)

        fun <T> error(message: String): Resource<T> =
            Resource(status = Status.ERROR, message = message)

    }
}

suspend fun <T, G> getData(
    serviceType: ServiceType,
    input: T,
    mainRepository: MainRepository
): Resource<G> {

    try {
        return withContext(Dispatchers.IO) {
            val response = when (serviceType) {
                ServiceType.ARTICLES -> mainRepository.getArticles()
                //other services will be added below
            }

            Resource.success( data = response as G)
        }

    } catch (exception: Exception) {

        //TODO check for apis error format
        return Resource.error(
            message = exception.message ?: "Fetch data error, please try again later"
        )

    }

}

