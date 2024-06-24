package com.example.ompokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.pokemon.list.POKEMON_LIST_ROUTE
import com.example.pokemon.list.pokemonListScreen
import com.example.pokemon.view.navigateToViewPokemon
import com.example.pokemon.view.viewPokemonScreen

@Composable
fun OMPokedexNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = POKEMON_LIST_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        pokemonListScreen(onPokemonClick = navController::navigateToViewPokemon)
        viewPokemonScreen(onBackClick = navController::popBackStack)
    }
}

