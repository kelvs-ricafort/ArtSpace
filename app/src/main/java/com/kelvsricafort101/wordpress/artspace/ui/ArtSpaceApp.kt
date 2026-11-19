package com.kelvsricafort101.wordpress.artspace.ui

import android.content.res.Configuration
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kelvsricafort101.wordpress.artspace.R
import com.kelvsricafort101.wordpress.artspace.navigation.ArtSpaceNavHost
import com.kelvsricafort101.wordpress.artspace.ui.screens.SettingsDataStore
import com.kelvsricafort101.wordpress.artspace.ui.screens.SettingsViewModel
import com.kelvsricafort101.wordpress.artspace.ui.screens.SettingsViewModelFactory
import com.kelvsricafort101.wordpress.artspace.ui.theme.ArtSpaceTheme
import java.util.Locale

/**
 * Top-level composable that represents screen for the app.
 */
@Composable
fun ArtSpaceApp(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val currentConfiguration = LocalConfiguration.current
    val activityResultRegistryOwner = LocalActivityResultRegistryOwner.current
    val settingsDataStore = remember{ SettingsDataStore(context = context.applicationContext) }
    val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(settingsDataStore = settingsDataStore))
    val settingsUiState by settingsViewModel.uiState.collectAsState()

    val configuration = remember(
        settingsUiState.appLanguage.code,
        currentConfiguration
    ) {
        Configuration(currentConfiguration).apply {
            setLocale(
                Locale.forLanguageTag(settingsUiState.appLanguage.code)
            )
        }
    }

    val localizedContext = remember(configuration) {
        context.createConfigurationContext(configuration)
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalConfiguration provides configuration,
        LocalActivityResultRegistryOwner provides activityResultRegistryOwner!!
    ) {
        ArtSpaceTheme(
            darkTheme = settingsUiState.darkMode
        ) {
            ArtSpaceNavHost(
                navController = navController,
                settingsUiState = settingsUiState,
                onDarkModeChanged = settingsViewModel::setDarkMode,
                onLanguageSelected = settingsViewModel::setLanguage
            )
        }
    }
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@Composable
fun ArtSpaceTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            } else {
                navigationIcon()
            }
        },
        actions = actions
    )
}