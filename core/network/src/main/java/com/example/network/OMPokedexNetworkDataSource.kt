package com.example.network

import com.example.model.Pokemon
import com.example.model.PokemonExpanded

interface OMPokedexNetworkDataSource {

    suspend fun getPokemon(limit: Int = 100): List<Pokemon>
    suspend fun getExpandedPokemon(name: String): PokemonExpanded
}