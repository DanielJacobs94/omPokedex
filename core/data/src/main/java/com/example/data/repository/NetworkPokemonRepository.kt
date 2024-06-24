package com.example.data.repository

import com.example.model.Pokemon
import com.example.model.PokemonExpanded
import com.example.network.OMPokedexNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class NetworkPokemonRepository @Inject constructor(private val network: OMPokedexNetworkDataSource) : PokemonRepository {
    override fun getPokemonList(): Flow<List<Pokemon>> = flow {
        emit(network.getPokemon())
    }

    override fun getExpandedPokemon(name: String): Flow<PokemonExpanded> = flow {
        emit(network.getExpandedPokemon(name = name))
    }
}