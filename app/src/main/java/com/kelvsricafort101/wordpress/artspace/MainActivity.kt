package com.kelvsricafort101.wordpress.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kelvsricafort101.wordpress.artspace.ui.ArtSpaceApp
import com.kelvsricafort101.wordpress.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme(darkTheme = false) {
        Surface {
            ArtSpaceApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppDarkPreview() {
    ArtSpaceTheme(darkTheme = true) {
        Surface {
            ArtSpaceApp()
        }
    }
}