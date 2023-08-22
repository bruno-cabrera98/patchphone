package com.bruno.patchphone.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.bruno.patchphone.components.MidiItem
import com.bruno.patchphone.controllers.MidiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MidiInputPage (midiController: MidiController, navController: NavController) {

    Column {

        TopAppBar(
            title = { Text(text = "Patchphone") },
            actions = {
                // Refresh icon (use an icon)
                IconButton(onClick = { midiController.getDevices() }) {
                    Icon( imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        )

        midiController.midiConnections.forEach {
            MidiItem(midiConnection = it, connect = { midiController.connect(it) }, disconnect = { midiController.disconnect(it) })
        }
    }

}
