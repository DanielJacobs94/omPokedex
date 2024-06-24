package com.example.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.PokemonRepository
import com.example.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _pokemonList = MutableStateFlow(emptyList<Pokemon>())
    val filteredPokemonList: StateFlow<List<Pokemon>> = searchText
        .combine(_pokemonList) { text, pokemonList ->
            if (text.isBlank()) {
                return@combine pokemonList
            }
            pokemonList.filter { pokemon ->
                pokemon.name.uppercase().contains(text.trim().uppercase())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uiState: StateFlow<PokemonListUiState> =
        pokemonRepository.getPokemonList()
            .onEach { pokemonList ->
                _pokemonList.value = pokemonList
            }
            .map(PokemonListUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PokemonListUiState.Loading,
            )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }
}

sealed interface PokemonListUiState {
    data object Loading: PokemonListUiState
    data class Success(
        val feed: List<Pokemon>,
    ) : PokemonListUiState
}