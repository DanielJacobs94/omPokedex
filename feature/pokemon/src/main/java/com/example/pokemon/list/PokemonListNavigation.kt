package com.example.pokemon.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val POKEMON_LIST_ROUTE = "pokemon_list_route"

fun NavGraphBuilder.pokemonListScreen(
    onPokemonClick: (String) -> Unit,
) {
    composable(
        route = POKEMON_LIST_ROUTE,
        arguments = listOf(),
    ) {
        PokemonListRoute(onPokemonClick = onPokemonClick)
    }
}