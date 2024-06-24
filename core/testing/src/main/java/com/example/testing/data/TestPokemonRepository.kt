package com.example.testing.data

import android.util.Log
import com.example.data.repository.PokemonRepository
import com.example.model.Pokemon
import com.example.model.PokemonExpanded
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestPokemonRepository : PokemonRepository {

    // Pokemon List
    private val pokemonFlow = MutableSharedFlow<List<Pokemon>>()

    fun setPokemon(pokemon: List<Pokemon>) {
        pokemonFlow.tryEmit(pokemon)
    }

    override fun getPokemonList(): Flow<List<Pokemon>> {
        return pokemonFlow.map { it }
    }

    // Expanded List
    private val pokemonExpandedFlow = MutableSharedFlow<PokemonExpanded>()

    fun setExpandedPokemon(pokemon: PokemonExpanded) {
        val success = pokemonExpandedFlow.tryEmit(pokemon)
    }

    override fun getExpandedPokemon(name: String): Flow<PokemonExpanded> {
        return pokemonExpandedFlow.map { it }
    }
}