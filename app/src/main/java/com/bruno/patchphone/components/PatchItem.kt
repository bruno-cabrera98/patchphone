package com.bruno.patchphone.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bruno.patchphone.datatypes.Patch

@Composable
fun PatchItem (patch: Patch, active: Boolean = false, onClick: () -> Unit) {
    // primary color when active
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(10.dp).clickable { onClick() }) {
        Text(text = patch.category, style = MaterialTheme.typography.labelLarge, color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
        Text(text = patch.name, style = MaterialTheme.typography.headlineMedium, color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
        Row (verticalAlignment = Alignment.CenterVertically) {
            Text(text = "MSB: " + patch.msb.toString(), modifier = Modifier.padding(end = 5.dp), style = MaterialTheme.typography.labelLarge, color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
            Text(text = "LSB: " + patch.lsb.toString(), modifier = Modifier.padding(end = 5.dp),style = MaterialTheme.typography.labelLarge, color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
            Text(text = "PC: " + patch.pc.toString(), modifier = Modifier.padding(end = 5.dp), style = MaterialTheme.typography.labelLarge, color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
        }
    }

}

@Preview
@Composable
fun PatchItemPreview() {
    var patch = Patch("Test", 0, 0, 0, "Test")
    var patch2 = Patch("Tessdat", 0, 0, 0, "Test")
    Column() {
        PatchItem(patch, onClick = {})
        PatchItem(patch2, active = true, onClick = {})

    }
}