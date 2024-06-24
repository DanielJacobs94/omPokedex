package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val name: String,
    val url: String
)

@Serializable
data class PokemonExpanded(
    val id: Long,
    val name: String,
    val abilities: List<Ability>?,
    val base_experience: Int,
    val cries: Cries?,
    val forms: List<Form>?,
    val game_indices: List<GameIndex>?,
    val height: Int,
    val weight: Int,
    val moves: List<Move>?,
    val species: Species?,
    val sprites: Sprites?,
    val stats: List<Stat>?,
    val types: List<Type>?,
    val past_types: List<PastType>?
)

@Serializable
data class Ability(
    val is_hidden: Boolean,
    val slot: Int,
    val ability: NamedApiResource?
)

@Serializable
data class Form(
    val name: String,
    val url: String
)

@Serializable
data class GameIndex(
    val game_index: Int,
    val version: NamedApiResource?
)

@Serializable
data class HeldItem(
    val item: NamedApiResource?,
    val version_details: List<VersionDetail>?
)

@Serializable
data class VersionDetail(
    val rarity: Int,
    val version: NamedApiResource?
)

@Serializable
data class Move(
    val move: NamedApiResource?,
    val version_group_details: List<VersionGroupDetail>?
)

@Serializable
data class VersionGroupDetail(
    val level_learned_at: Int,
    val version_group: NamedApiResource?,
    val move_learn_method: NamedApiResource?
)

@Serializable
data class Species(
    val name: String,
    val url: String
)

@Serializable
data class Sprites(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?,
    val other: OtherSprites?
)

@Serializable
data class OtherSprites(
    val dream_world: DreamWorldSprites?,
    val home: HomeSprites,
    val showdown: ShowdownSprites
)

@Serializable
data class DreamWorldSprites(
    val front_default: String?,
    val front_female: String?
)

@Serializable
data class HomeSprites(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@Serializable
data class OfficialArtworkSprites(
    val front_default: String?,
    val front_shiny: String?
)

@Serializable
data class ShowdownSprites(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@Serializable
data class VersionSprites(
    val generation_i: GenerationISprites?,
    val generation_ii: GenerationIISprites?,
    val generation_iii: GenerationIIISprites?,
    val generation_iv: GenerationIVSprites?,
    val generation_v: GenerationVSprites?,
    val generation_vi: GenerationVISprites?,
    val generation_vii: GenerationVIISprites?,
    val generation_viii: GenerationVIIISprites?
)

@Serializable
data class GenerationISprites(
    val red_blue: GenerationSpriteDetails?,
    val yellow: GenerationSpriteDetails?
)

@Serializable
data class GenerationIISprites(
    val crystal: GenerationSpriteDetails?,
    val gold: GenerationSpriteDetails?,
    val silver: GenerationSpriteDetails?
)

@Serializable
data class GenerationIIISprites(
    val emerald: GenerationSpriteDetails?,
    val firered_leafgreen: GenerationSpriteDetails?,
    val ruby_sapphire: GenerationSpriteDetails?
)

@Serializable
data class GenerationIVSprites(
    val diamond_pearl: GenerationSpriteDetails?,
    val heartgold_soulsilver: GenerationSpriteDetails?,
    val platinum: GenerationSpriteDetails?
)

@Serializable
data class GenerationVSprites(
    val black_white: BlackWhiteSprites?
)

@Serializable
data class GenerationVISprites(
    val omegaruby_alphasapphire: GenerationSpriteDetails?,
    val x_y: GenerationSpriteDetails?
)

@Serializable
data class GenerationVIISprites(
    val icons: GenerationSpriteDetails?,
    val ultra_sun_ultra_moon: GenerationSpriteDetails?
)

@Serializable
data class GenerationVIIISprites(
    val icons: GenerationSpriteDetails?
)

@Serializable
data class GenerationSpriteDetails(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?,
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_gray: String?,
    val back_gray: String?
)

@Serializable
data class BlackWhiteSprites(
    val animated: AnimatedSprites?,
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@Serializable
data class AnimatedSprites(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

@Serializable
data class Cries(
    val latest: String,
    val legacy: String
)

@Serializable
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: NamedApiResource?
)

@Serializable
data class Type(
    val slot: Int,
    val type: NamedApiResource?
)

@Serializable
data class NamedApiResource(
    val name: String,
    val url: String
)

@Serializable
data class PastType(
    val generation: NamedApiResource?,
    val types: List<Type>
)
