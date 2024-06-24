package com.example.pokemon.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.PokemonExpanded
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.model.Stat
import com.example.pokemon.R
import java.util.Locale

@Composable
fun ViewPokemonRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: ViewPokemonViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ViewPokemonScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
internal fun ViewPokemonScreen(
    uiState: ViewPokemonUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
        ) {
            when (uiState) {
                ViewPokemonUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(80.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                }
                is ViewPokemonUiState.Success -> {
                    ViewPokemonToolbar(
                        onBackClick = onBackClick
                    )
                    PokemonView(pokemon = uiState.pokemon)
                }
            }
        }
    }
}

@Composable
private fun PokemonView(
    pokemon: PokemonExpanded) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Column {
            if (!pokemon.sprites?.front_default.isNullOrEmpty()) {
                Row {
                    PokemonImage(pokemon.sprites?.front_default)
                }
            }
            Box(
                modifier = Modifier.padding(8.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(pokemon.name.capitalize(Locale.ROOT), style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(14.dp))
                    pokemon.stats?.let {
                        PokemonStatsRow(it)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }
    }
}

@Composable
private fun ViewPokemonToolbar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun PokemonImage(
    imageUrl: String?,
    height: Int = 180
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )
    val isLocalInspection = LocalInspectionMode.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp),
                color = MaterialTheme.colorScheme.tertiary,
            )
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp),
            contentScale = ContentScale.FillHeight,
            painter = if (isError.not() && !isLocalInspection) {
                imageLoader
            } else {
                painterResource(R.drawable.ic_launcher_background)
            },
            contentDescription = null,
        )
    }
}

@Composable
fun PokemonStatsRow(stats: List<Stat>) {
    stats.forEach {
        ItemStats(it, modifier = Modifier)
    }
}

@Composable
fun ItemStats(stat: Stat,
              modifier: Modifier) {
    val statName = stat.stat?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    val statDisplay = "$statName: ${stat.base_stat}"

    Box(modifier = modifier) {
        TextButton(
            onClick = { },
            enabled = false
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                Text(text = statDisplay)
            }
        }
    }
}