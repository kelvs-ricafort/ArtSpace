package com.kelvsricafort101.wordpress.artspace.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Artwork(
    @StringRes val title: Int,
    @StringRes val artist: Int,
    val year: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val description: Int
)
