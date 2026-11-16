package com.kelvsricafort101.wordpress.artspace.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelvsricafort101.wordpress.artspace.R
import com.kelvsricafort101.wordpress.artspace.data.Artwork
import com.kelvsricafort101.wordpress.artspace.navigation.NavigationDestination
import com.kelvsricafort101.wordpress.artspace.ui.ArtSpaceTopAppBar
import com.kelvsricafort101.wordpress.artspace.ui.theme.ArtSpaceTheme

object HomeDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}
/**
 * Entry Route for HomeScreen.
 */
@Composable
fun HomeScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArtSpaceTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(
                        onClick = openDrawer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(R.string.open_navigation_drawer)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        HomeBody(
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    modifier: Modifier = Modifier
) {
    // Use remember to hold the state across recompositions.
    var currentArtworkIndex by remember { mutableIntStateOf(0) }

    // Use a data class to hold artwork details. This is more organized.
    val artworks = listOf(
        Artwork(
            title = "Mona Lisa",
            artist = "Leonardo da Vinci",
            year = 1503,
            imageRes = R.drawable.mona_lisa,
            description = "The Mona Lisa is a half-length portrait painting by Italian artist Leonardo da Vinci."
        ),
        Artwork(
            title = "The Starry Night",
            artist = "Vincent van Gogh",
            year = 1889,
            imageRes = R.drawable.starry_night,
            description = "The Starry Night is an oil painting by the Dutch post-impressionist painter Vincent van Gogh."
        ),
        Artwork(
            title = "The Scream",
            artist = "Edvard Munch",
            year = 1893,
            imageRes = R.drawable.the_scream,
            description = "The Scream is a 1893 oil painting by Norwegian artist Edvard Munch"
        ),
        Artwork(
            title = "Mount Mayon",
            artist = "Kelvin Ricafort",
            year = 2023,
            imageRes = R.drawable.mount_mayon,
            description = "Mount Mayon is an active stratovolcano in the province of Albay in Bicol, Philippines. A popular tourist spot, it is renowned for its perfect cone because of its symmetric conical shape"
        ),
        Artwork(
            title = "Shadow",
            artist = "Kelvin Ricafort",
            year = 2023,
            imageRes = R.drawable.shadow,
            description = "Shadow is my 1st pet cat."
        ),
        Artwork(
            title = "Ampi",
            artist = "Kelvin Ricafort",
            year = 2024,
            imageRes = R.drawable.ampi,
            description = "Ampi is my 2nd pet cat."
        ),
        Artwork(
            title = "Adolfo",
            artist = "Kelvin Ricafort",
            year = 2025,
            imageRes = R.drawable.adolfo,
            description = "Adolfo is my 3rd pet cat."
        ),
        Artwork(
            title = "Zombie",
            artist = "Kelvin Ricafort",
            year = 2025,
            imageRes = R.drawable.zombie,
            description = "Zombie is my 4th pet cat."
        ),
        Artwork(
            title = "Maru",
            artist = "Kelvin Ricafort",
            year = 2025,
            imageRes = R.drawable.maru,
            description = "Maru is my 5th pet cat."
        ),
        Artwork(
            title = "Gandalf",
            artist = "Kelvin Ricafort",
            year = 2025,
            imageRes = R.drawable.gandalf,
            description = "Gandalf is my 6th pet cat."
        )
    )
    // Get the current artwork based on the index.
    val currentArtwork = artworks[currentArtworkIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Use .clip to apply rounded corners to the Image.
        Image(
            painter = painterResource(currentArtwork.imageRes),
            contentDescription = stringResource(R.string.artwork_content_description, currentArtwork.title),
            modifier = Modifier
                .size(400.dp) // Increased size for better display
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)), // Apply rounded corners. Removed for now, see comment below
            contentScale = ContentScale.Crop // Removed contentScale the images are of different sizes and this was causing issues
        )
        Spacer(modifier = Modifier.height(16.dp)) // Removed Spacer and added padding to the Text.
        Text(
            text = currentArtwork.title,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 24.sp
        )
        Text(
            text = stringResource(R.string.artist_and_year, currentArtwork.artist, currentArtwork.year),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 16.sp
        )
        Text(
            text = currentArtwork.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 16.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                // Use modulo to loop around the list correctly.
                currentArtworkIndex = (currentArtworkIndex - 1 + artworks.size) % artworks.size
            }) {
                Text("Previous")
            }
            Button(onClick = {
                // Use modulo to loop around the list correctly.
                currentArtworkIndex = (currentArtworkIndex + 1) % artworks.size
            }) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ArtSpaceTheme(darkTheme = false) {
        Surface {
            HomeScreen(
                openDrawer = {},
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenDarkPreview() {
    ArtSpaceTheme(darkTheme = true) {
        Surface {
            HomeScreen(
                openDrawer = {},
                modifier = Modifier
            )
        }
    }
}