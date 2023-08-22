package com.bruno.patchphone.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bruno.patchphone.R
import com.bruno.patchphone.datatypes.MidiConnection
import com.bruno.patchphone.datatypes.MidiConnectionStatus

@Composable
fun MidiItem(midiConnection: MidiConnection, connect: () -> Unit, disconnect: () -> Unit) {
    fun action() {
        if (midiConnection.status.value == MidiConnectionStatus.DISCONNECTED)
            connect()
        else if (midiConnection.status.value == MidiConnectionStatus.CONNECTED)
            disconnect()
    }

    Column(
        Modifier.padding(8.dp)
    ) {
        Row (
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        )
        {
            IconButton(onClick = { action() }) {
                if (midiConnection.status.value == MidiConnectionStatus.DISCONNECTED)
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_wifi_24),
                        contentDescription = "Connect",
                    )
                else if (midiConnection.status.value == MidiConnectionStatus.CONNECTED)
                     Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Disconnect",
                    )
                else if (midiConnection.status.value == MidiConnectionStatus.CONNECTING)
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                        contentDescription = "Connecting",
                    )
            }
            Column {
                Text(midiConnection.name, style = MaterialTheme.typography.titleMedium, color = if (midiConnection.status.value == MidiConnectionStatus.CONNECTED) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground)

                if (midiConnection.status.value == MidiConnectionStatus.CONNECTED)
                    Text("Connected", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }


        }
        if (midiConnection.status.value == MidiConnectionStatus.CONNECTED) {
            Divider(
                Modifier.border(1.dp, MaterialTheme.colorScheme.primary, CutCornerShape(1.dp))
            )
        }


    }


}

@Preview
@Composable
fun MidiItemPreview() {
    Column() {
        MidiItem(MidiConnection("Test", MidiConnectionStatus.CONNECTED), {}, {})
        MidiItem(MidiConnection("Test", MidiConnectionStatus.DISCONNECTED), {}, {})
        MidiItem(MidiConnection("Test", MidiConnectionStatus.CONNECTING), {}, {})
        
    }
}