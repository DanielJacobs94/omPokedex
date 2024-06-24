package com.example.pokemon.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val POKEMON_NAME_ARG = "name"
const val VIEW_POKEMON_ROUTE_BASE = "view_pokemon_route"
const val VIEW_POKEMON_ROUTE = "$VIEW_POKEMON_ROUTE_BASE?$POKEMON_NAME_ARG={$POKEMON_NAME_ARG}"

fun NavController.navigateToViewPokemon(pokemonName: String, navOptions: NavOptions? = null) {
    val route = "${VIEW_POKEMON_ROUTE_BASE}?${POKEMON_NAME_ARG}=$pokemonName"
    navigate(route, navOptions)
}

fun NavGraphBuilder.viewPokemonScreen(
    onBackClick: () -> Unit,) {
    composable(
        route = VIEW_POKEMON_ROUTE,
        arguments = listOf(
            navArgument(POKEMON_NAME_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
        ),
    ) {
        ViewPokemonRoute(onBackClick = onBackClick)
    }
}