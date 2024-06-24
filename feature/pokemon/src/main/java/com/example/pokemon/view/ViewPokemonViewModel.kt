package com.example.pokemon.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.PokemonRepository
import com.example.model.PokemonExpanded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ViewPokemonViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val pokemonName: StateFlow<String?> = savedStateHandle.getStateFlow(POKEMON_NAME_ARG, null)

    val uiState: StateFlow<ViewPokemonUiState> =
        pokemonRepository.getExpandedPokemon(name = pokemonName.value ?: "")
            .map(ViewPokemonUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ViewPokemonUiState.Loading,
            )
}

sealed interface ViewPokemonUiState {
    data object Loading: ViewPokemonUiState
    data class Success(
        val pokemon: PokemonExpanded,
    ) : ViewPokemonUiState
}