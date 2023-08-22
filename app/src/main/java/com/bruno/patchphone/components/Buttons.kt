package com.bruno.patchphone.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// squared icon button
@Composable
fun NextButton(onClick : () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next", tint = MaterialTheme.colorScheme.onBackground)
    }

}

@Preview
@Composable
fun NextButtonPreview() {
    NextButton(onClick = {})
}

@Composable
fun PreviousButton(onClick : () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous", tint = MaterialTheme.colorScheme.onBackground)
    }

}

@Preview
@Composable
fun PreviousButtonPreview() {
    PreviousButton(onClick = {})
}