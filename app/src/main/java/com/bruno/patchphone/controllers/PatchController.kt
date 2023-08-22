package com.bruno.patchphone.controllers

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.bruno.patchphone.datatypes.Patch
import com.bruno.patchphone.datatypes.PatchCategory
import java.io.InputStream


class PatchController (midiController: MidiController) {
    private val midiController = midiController

    val patches = mutableStateListOf<Patch>()

    // store patched grouped by categories
    val categories = mutableStateListOf<PatchCategory>()

    var currentPatch by mutableStateOf<Patch?>(null)

    fun loadPatches(context: Context) {
        val inputStream: InputStream = context.assets.open("prst_patches.txt")
        val inputString = inputStream.bufferedReader().use { it.readText() }
        patches.addAll(parseFile(inputString))

        // group patches by category
        categories.addAll(patches.groupBy { it.category }.map { PatchCategory(it.key, it.value.toMutableList()) })

        currentPatch = patches.firstOrNull()
    }

    fun nextPatch() {
        val index = patches.indexOf(currentPatch)
        if (index < patches.size - 1) {
            currentPatch = patches[index + 1]
        }
        midiController.set_patch(currentPatch!!)
    }

    fun previousPatch() {
        val index = patches.indexOf(currentPatch)
        if (index > 0) {
            currentPatch = patches[index - 1]
        }
        midiController.set_patch(currentPatch!!)
    }

    fun setPatch(patch: Patch) {
        currentPatch = patch
        midiController.set_patch(currentPatch!!)
    }

    /**
     * The idea is to parse a file called prst_patches.txt with the current format
     *   - # Category Name (When a line is formatted like that, this is the name of the category of the following patches)
     *   - 87 64 73 "Tine EP" (Example) - MSB LSB PC "Patch Name" (ignore the quotes when saving the patch name)
     */
    private fun parseFile(file: String): List<Patch> {
        val lines = file.split("\n")
        val patches = mutableListOf<Patch>()
        var currentCategory = ""
        for (line in lines) {
            if (line.startsWith("#")) {
                currentCategory = line.substring(2)
            } else {
                val patch = Patch()
                val split = line.split(" ")
                patch.category = currentCategory
                patch.msb = split[0].toInt()
                patch.lsb = split[1].toInt()
                patch.pc = split[2].toInt()
                patch.name = split.subList(3, split.size).joinToString(" ").replace("\"", "")
                patches.add(patch)
            }
        }
        return patches
    }
}