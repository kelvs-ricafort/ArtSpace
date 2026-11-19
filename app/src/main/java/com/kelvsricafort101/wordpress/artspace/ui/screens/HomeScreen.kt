package com.kelvsricafort101.wordpress.artspace.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val bitmap = if (
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
                ) {
                    val source = ImageDecoder.createSource(
                        context.contentResolver, uri
                    )
                    ImageDecoder.decodeBitmap(source) { decoder, info, _ ->
                        val maxDimension = 2048
                        val scale = minOf(
                            1f,
                            maxDimension.toFloat() / maxOf(
                                info.size.width,
                                info.size.height
                            )
                        )

                        decoder.setTargetSize(
                            (info.size.width * scale).toInt(),
                            (info.size.height * scale).toInt()
                        )
                    }
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(
                        context.contentResolver, uri
                    )
                }
                selectedImage = bitmap
            } catch (_: Exception) {
                selectedImage = null
            }
        }
    }
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
            selectedImage = selectedImage,
            onChooseImage = {
                galleryLauncher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            onImageChanged = { selectedImage = it },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeBody(
    selectedImage: Bitmap?,
    onChooseImage: () -> Unit,
    onImageChanged: (Bitmap?) -> Unit,
    modifier: Modifier = Modifier
) {
    // Use remember to hold the state across recompositions.
    var currentArtworkIndex by remember { mutableIntStateOf(0) }

    // Use a data class to hold artwork details. This is more organized.
    val artworks = listOf(
        Artwork(
            title = R.string.artwork_mona_lisa,
            artist = R.string.artist_leonardo_da_vinci,
            year = 1503,
            imageRes = R.drawable.mona_lisa,
            description = R.string.artwork_mona_lisa_description
        ),
        Artwork(
            title = R.string.artwork_starry_night,
            artist = R.string.artist_vincent_van_gogh,
            year = 1889,
            imageRes = R.drawable.starry_night,
            description = R.string.artwork_starry_night_description
        ),
        Artwork(
            title = R.string.artwork_the_scream,
            artist = R.string.artist_edvard_munch,
            year = 1893,
            imageRes = R.drawable.the_scream,
            description = R.string.artwork_the_scream_description
        ),
        Artwork(
            title = R.string.artwork_mount_mayon,
            artist = R.string.artist_kelvin_ricafort,
            year = 2023,
            imageRes = R.drawable.mount_mayon,
            description = R.string.artwork_mount_mayon_description
        ),
        Artwork(
            title = R.string.artwork_shadow,
            artist = R.string.artist_kelvin_ricafort,
            year = 2023,
            imageRes = R.drawable.shadow,
            description = R.string.artwork_shadow_description
        ),
        Artwork(
            title = R.string.artwork_ampi,
            artist = R.string.artist_kelvin_ricafort,
            year = 2024,
            imageRes = R.drawable.ampi,
            description = R.string.artwork_ampi_description
        ),
        Artwork(
            title = R.string.artwork_adolfo,
            artist = R.string.artist_kelvin_ricafort,
            year = 2025,
            imageRes = R.drawable.adolfo,
            description = R.string.artwork_adolfo_description
        ),
        Artwork(
            title = R.string.artwork_zombie,
            artist = R.string.artist_kelvin_ricafort,
            year = 2025,
            imageRes = R.drawable.zombie,
            description = R.string.artwork_zombie_description
        ),
        Artwork(
            title = R.string.artwork_maru,
            artist = R.string.artist_kelvin_ricafort,
            year = 2025,
            imageRes = R.drawable.maru,
            description = R.string.artwork_maru_description
        ),
        Artwork(
            title = R.string.artwork_gandalf,
            artist = R.string.artist_kelvin_ricafort,
            year = 2025,
            imageRes = R.drawable.gandalf,
            description = R.string.artwork_gandalf_description
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
        // Artwork Image
        if (selectedImage != null) {
            Image(
                bitmap = selectedImage!!.asImageBitmap(),
                contentDescription = stringResource(R.string.selected_gallery_image),
                modifier = Modifier
                    .size(400.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )
        } else {
            // Use .clip to apply rounded corners to the Image.
            Image(
                painter = painterResource(currentArtwork.imageRes),
                contentDescription = stringResource(
                    R.string.artwork_content_description,
                    currentArtwork.title
                ),
                modifier = Modifier
                    .size(400.dp) // Increased size for better display
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp)), // Apply rounded corners. Removed for now, see comment below
                contentScale = ContentScale.Fit // Removed contentScale the images are of different sizes and this was causing issues
            )
        }
        Spacer(modifier = Modifier.height(16.dp)) // Removed Spacer and added padding to the Text.
        Text(
            text = stringResource(currentArtwork.title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 24.sp
        )
        Text(
            text = stringResource(R.string.artist_and_year, stringResource(currentArtwork.artist), currentArtwork.year),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 16.sp
        )
        Text(
            text = stringResource(currentArtwork.description),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 16.sp
        )
        // Gallery Picker
        OutlinedButton(
            onClick = onChooseImage
        ) {
            Icon(
                imageVector = Icons.Default.PhotoLibrary,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.choose_from_gallery)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    currentArtworkIndex = (currentArtworkIndex - 1 + artworks.size) % artworks.size
                    onImageChanged(null)
                }
            ) {
                Text(
                    text = stringResource(R.string.previous)
                )
            }

            Button(
                onClick = {
                    currentArtworkIndex = (currentArtworkIndex + 1) % artworks.size
                    onImageChanged(null)
                }
            ) {
                Text(
                    text = stringResource(R.string.next)
                )
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