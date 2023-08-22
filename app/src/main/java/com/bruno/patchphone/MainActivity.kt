package com.bruno.patchphone

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bruno.patchphone.controllers.MidiController
import com.bruno.patchphone.controllers.PatchController
import com.bruno.patchphone.pages.ControllerPage
import com.bruno.patchphone.pages.MidiInputPage
import com.bruno.patchphone.pages.PatchPage
import com.bruno.patchphone.ui.theme.PatchphoneTheme

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector?) {
    // music icon
    object PatchPage : Screen("patch", R.string.patch_list_title, Icons.Default.PlayArrow)
    object MidiPage  : Screen("midi", R.string.midi_port_list_title, Icons.Default.List)
    object ControllerPage  : Screen("controller", R.string.controller_list_title, Icons.Default.Settings)
}

class MainActivity : ComponentActivity() {
    private val midiController = MidiController()
    private val patchController = PatchController(midiController)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        midiController.set_context(applicationContext)
        loadPatches()
        midiController.getDevices()

        setContent {

            PatchphoneTheme {

                val navController = rememberNavController()

                val items = listOf(
                    Screen.PatchPage,
                    Screen.MidiPage,
                    Screen.ControllerPage,
                )

                Scaffold(
                    topBar = {

                    },
                    bottomBar = {
                        NavigationBar (
                            containerColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.background,
                            modifier = Modifier.height(64.dp)                        )
                        {
                            Row(
                                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                            ) {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                items.forEach { screen ->
                                    BottomNavigationItem(
                                        icon = { Icon(screen.icon!!, contentDescription = null) },
                                        label = { Text(stringResource(screen.resourceId)) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        selectedContentColor = MaterialTheme.colorScheme.primary,
                                        unselectedContentColor = MaterialTheme.colorScheme.background,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }
                                        }
                                    )
                                }

                            }

                        }
                    }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = "patch", Modifier.padding(innerPadding)) {
                        composable("patch") {
                            PatchPage(patchController, navController)
                        }
                        composable("midi") {
                            MidiInputPage(midiController, navController)
                        }
                        composable("controller") {
                            ControllerPage(midiController)
                        }
                    }
                }

            }
        }
    }
    private fun loadPatches() {
        patchController.loadPatches(applicationContext)
    }
}
