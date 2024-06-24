package com.example.pokemon.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Pokemon

@Composable
fun PokemonListRoute(
    onPokemonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()) {

    val feedState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val filteredPokemonList by viewModel.filteredPokemonList.collectAsStateWithLifecycle()

    PokemonListScreen(
        viewModel = viewModel,
        feedState = feedState,
        searchText = searchText,
        isSearching = isSearching,
        filteredPokemonList = filteredPokemonList,
        onPokemonClick = onPokemonClick,
        modifier = modifier
    )
}

@Composable
internal fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    feedState: PokemonListUiState,
    searchText: String,
    isSearching: Boolean,
    filteredPokemonList: List<Pokemon>,
    onPokemonClick: (String) -> Unit,
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        when (feedState) {
            PokemonListUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(80.dp),
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
            is PokemonListUiState.Success -> {
                Column(
                    modifier = Modifier
                ) {
                    if (isSearching) {
                        PokemonSearchBar(searchText = searchText,
                            onSearchTextChange = viewModel::onSearchTextChange,
                            onToggleSearch = viewModel::onToggleSearch,
                            pokemonList = filteredPokemonList,
                            onPokemonClick = onPokemonClick)
                    } else {
                        PokemonListToolbar(onSearchClick = {
                            viewModel.onToggleSearch()
                        })
                        Text(
                            text = "Pokemon",
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                        PokemonList(pokemon = feedState.feed) {
                            onPokemonClick(it)
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonSearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    pokemonList: List<Pokemon>,
    onPokemonClick: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    SearchBar(
        query = searchText,
        onQueryChange = onSearchTextChange,
        onSearch = { keyboardController?.hide() },
        active = true,
        onActiveChange = { },
        modifier = Modifier,
        placeholder = {
            Text(text = "Search Pokemon")
        },
        leadingIcon = {
            IconButton(onClick = { onToggleSearch() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { onSearchTextChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        content = {
            PokemonList(pokemon = pokemonList) {
                onPokemonClick(it)
            }
        }
    )
}

@Composable
private fun PokemonList(
    pokemon: List<Pokemon>,
    onPokemonClick: (String) -> Unit) {
    val scrollableState = rememberLazyListState()
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        state = scrollableState,
    ) {
        pokemon.forEach { pokemon ->
            item {
                PokemonItem(item = pokemon,
                    onPokemonClick = onPokemonClick)
            }
        }
    }
}

@Composable
private fun PokemonItem(
    item: Pokemon,
    onPokemonClick: (String) -> Unit) {
    Surface {
        ListItem(
            headlineContent = {
                Text(text = item.name.capitalize())
            },
            trailingContent = {
                IconButton(onClick = { onPokemonClick(item.name) }) {
                    Icon(Icons.AutoMirrored.Sharp.KeyboardArrowRight, contentDescription = null)
                }
            },
            colors = ListItemDefaults.colors(Color.Transparent),
            modifier = Modifier
                .clickable(enabled = true, onClick = {
                    onPokemonClick(item.name)
                }),
        )
    }
}

@Composable
private fun PokemonListToolbar(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.width(1.dp))
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = Icons.Sharp.Search,
                contentDescription = null,
            )
        }
    }
}