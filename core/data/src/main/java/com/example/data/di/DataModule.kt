package com.example.data.di

import com.example.data.repository.NetworkPokemonRepository
import com.example.data.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsPokemonRepository(
        pokemonRepository: NetworkPokemonRepository,
    ): PokemonRepository
}