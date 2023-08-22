package com.bruno.patchphone.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bruno.patchphone.controllers.MidiController

@Composable
fun ControllerPage(midiController: MidiController) {

    val volume = remember { mutableStateOf(0f) }
    // starts at 64
    val pan = remember { mutableStateOf(64f) }
    val reverb = remember { mutableStateOf(0f) }
    var chorus = remember { mutableStateOf(0f) }

    Column {

        // Volume slider
        Surface(
            modifier = Modifier.padding(20.dp)
        ) {
            Text("Volume")
            Slider(value = volume.value, onValueChange = { midiController.setVolume(it.toInt()); volume.value = it }, valueRange = 0f..127f, steps = 127, modifier = Modifier.padding(top = 20.dp))
        }

        // Pan slider
        Surface(
            modifier = Modifier.padding(20.dp)
        ) {
            Text("Pan")
            Slider(value = pan.value, onValueChange = { midiController.setPan(it.toInt()); pan.value = it }, valueRange = 0f..127f, steps = 127, modifier = Modifier.padding(top = 20.dp))
        }

        // Reverb slider
        Surface(
            modifier = Modifier.padding(20.dp)
        ) {
            Text("Reverb")
            Slider(value = reverb.value, onValueChange = { midiController.setReverb(it.toInt()); reverb.value = it }, valueRange = 0f..127f, steps = 127, modifier = Modifier.padding(top = 20.dp))
        }

        // Chorus slider
        Surface(
            modifier = Modifier.padding(20.dp)
        ) {
            Text("Chorus")
            Slider(value = chorus.value, onValueChange = { midiController.setChorus(it.toInt()); chorus.value = it }, valueRange = 0f..127f, steps = 127, modifier = Modifier.padding(top = 20.dp))
        }



    }


}