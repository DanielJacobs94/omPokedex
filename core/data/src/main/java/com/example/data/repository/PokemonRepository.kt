package com.example.data.repository

import com.example.model.Pokemon
import com.example.model.PokemonExpanded
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<List<Pokemon>>
    fun getExpandedPokemon(name: String): Flow<PokemonExpanded>
}