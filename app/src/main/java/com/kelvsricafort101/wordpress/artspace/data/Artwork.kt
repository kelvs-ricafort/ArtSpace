package com.kelvsricafort101.wordpress.artspace.data

import androidx.annotation.DrawableRes

data class Artwork(
    val title: String,
    val artist: String,
    val year: Int,
    @DrawableRes val imageRes: Int,
    val description: String
)
