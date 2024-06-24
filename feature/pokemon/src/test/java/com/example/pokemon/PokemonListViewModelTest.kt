package com.example.pokemon

import com.example.model.Pokemon
import com.example.pokemon.list.PokemonListUiState
import com.example.pokemon.list.PokemonListViewModel
import com.example.testing.data.TestPokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    private val pokemonRepository: TestPokemonRepository = TestPokemonRepository()
    private lateinit var viewModel: PokemonListViewModel

    @Before
    fun setup() {
        viewModel = PokemonListViewModel(
            pokemonRepository = pokemonRepository)
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertEquals(
            PokemonListUiState.Loading,
            viewModel.uiState.value,
        )
        assertEquals(PokemonListUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun stateIsLoadingWhenPokemonListIsLoading() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        pokemonRepository.setPokemon(samplePokemon)

        assertEquals(
            PokemonListUiState.Loading,
            viewModel.uiState.value,
        )
        assertEquals(PokemonListUiState.Loading, viewModel.uiState.value)

        collectJob.cancel()
    }

    @Test
    fun uiStateIsLoadingWhenPokemonIsLoading() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        pokemonRepository.setPokemon(emptyList())

        assertEquals(
            PokemonListUiState.Loading,
            viewModel.uiState.value,
        )
        pokemonRepository.setPokemon(emptyList())

        assertEquals(PokemonListUiState.Success(emptyList()), viewModel.uiState.value)

        collectJob.cancel()
    }
}

private val samplePokemon = listOf(
    Pokemon(name = "Bulbasaur",
        url = ""),
    Pokemon(name = "Charmander",
        url = ""),
    Pokemon(name = "Squirtle",
        url = "")
)