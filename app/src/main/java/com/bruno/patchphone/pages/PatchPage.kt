package com.bruno.patchphone.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bruno.patchphone.components.PatchItem
import com.bruno.patchphone.controllers.PatchController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PatchPage(patchController: PatchController, navController: NavController) {
    val rowState = rememberLazyListState()

    val snappingLayout = remember(rowState) { SnapLayoutInfoProvider(rowState) }
    val flingBehavior = rememberSnapFlingBehavior(snappingLayout)
    // Each row a category
    LazyRow(
    flingBehavior = flingBehavior,
    state = rowState,
    modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = patchController.categories.size,
            key = {
                patchController.categories[it].name
            }, itemContent = { index ->
                val state = rememberLazyListState()
                Column() {
                    Text(text = patchController.categories[index].name, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        state = state,
                        userScrollEnabled = true,
                        horizontalAlignment = Alignment.Start
                    ) {

                        itemsIndexed(
                            items = patchController.categories[index].patches,
                            key = { _, patch ->
                                patch.name
                            },
                            itemContent = { _, patch ->
                                PatchItem(
                                    patch = patch,
                                    active = patch == patchController.currentPatch
                                ) {
                                    patchController.setPatch(patch)
                                }
                            }
                        )
                    }
                }

            })

    }

}