package com.example.pokemon

import androidx.lifecycle.SavedStateHandle
import com.example.model.PokemonExpanded
import com.example.pokemon.view.ViewPokemonUiState
import com.example.pokemon.view.ViewPokemonViewModel
import com.example.testing.data.TestPokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.collect
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ViewPokemonViewModelTest {

    private val pokemonRepository: TestPokemonRepository = TestPokemonRepository()
    private lateinit var viewModel: ViewPokemonViewModel
    private val savedStateHandle = SavedStateHandle()

    @Before
    fun setup() {
        viewModel = ViewPokemonViewModel(
            savedStateHandle = savedStateHandle,
            pokemonRepository = pokemonRepository)
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertEquals(
            ViewPokemonUiState.Loading,
            viewModel.uiState.value,
        )
        assertEquals(ViewPokemonUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun stateIsLoadingWhenPokemonIsLoading() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        pokemonRepository.setExpandedPokemon(sampleExpandedPokemon)

        assertEquals(
            ViewPokemonUiState.Loading,
            viewModel.uiState.value,
        )
        assertEquals(ViewPokemonUiState.Loading, viewModel.uiState.value)

        collectJob.cancel()
    }

    @Test
    fun uiStateIsLoadingWhenPokemonIsLoading() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        assertEquals(
            ViewPokemonUiState.Loading,
            viewModel.uiState.value,
        )

        pokemonRepository.setExpandedPokemon(sampleExpandedPokemon)

        assertEquals(ViewPokemonUiState.Success(sampleExpandedPokemon), viewModel.uiState.value)

        collectJob.cancel()
    }
}

private val sampleExpandedPokemon = PokemonExpanded(
    id = 1,
    name = "Bulbasaur",
    abilities = emptyList(),
    base_experience = 10,
    cries = null,
    forms = emptyList(),
    game_indices = emptyList(),
    height = 10,
    weight = 10,
    moves = emptyList(),
    species = null,
    sprites = null,
    stats = emptyList(),
    types = emptyList(),
    past_types = emptyList())
