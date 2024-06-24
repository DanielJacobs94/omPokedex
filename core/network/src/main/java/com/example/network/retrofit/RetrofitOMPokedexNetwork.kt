package com.example.network.retrofit

import com.example.model.Pokemon
import com.example.model.PokemonExpanded
import com.example.network.BuildConfig
import com.example.network.OMPokedexNetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for NIA Network API
 */
private interface RetrofitOMPokedexNetworkApi {
    @GET(value = "pokemon/")
    suspend fun getPokemon(
        @Query("limit") limit: Int,
    ): NetworkResponse<List<Pokemon>>

    @GET(value = "pokemon/{name}")
    suspend fun getExpandedPokemon(
        @Path("name") name: String,
    ): PokemonExpanded
}

private const val OMP_BASE_URL = BuildConfig.BACKEND_URL

@Serializable
private data class NetworkResponse<T>(
    val results: T,
)

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModule {

    @Binds
    fun binds(impl: RetrofitOMPokedexNetwork): OMPokedexNetworkDataSource
}

@Singleton
internal class RetrofitOMPokedexNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : OMPokedexNetworkDataSource {

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(OMP_BASE_URL)
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitOMPokedexNetworkApi::class.java)

    override suspend fun getPokemon(limit: Int): List<Pokemon> {
        return networkApi.getPokemon(limit = limit).results
    }

    override suspend fun getExpandedPokemon(name: String): PokemonExpanded {
        return networkApi.getExpandedPokemon(name = name)
    }
}