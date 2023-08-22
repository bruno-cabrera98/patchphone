package com.bruno.patchphone.controllers

import android.content.Context
import android.content.pm.PackageManager
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiManager
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import com.bruno.patchphone.datatypes.MidiConnection
import com.bruno.patchphone.datatypes.Patch


class MidiController {

    fun set_context (context: Context) {
        this.context = context
    }

    lateinit var context: Context

    var midiConnections = mutableStateListOf<MidiConnection>()
    var currentMidiConnection = MutableLiveData<MidiConnection>()

    fun getDevices() {
        midiConnections.clear()
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MIDI)) {
            val m = context.getSystemService(Context.MIDI_SERVICE) as MidiManager
            val devices: Array<MidiDeviceInfo> = m.getDevices()
            devices.forEach { device ->
                val connection = MidiConnection(device)
                midiConnections.add(connection)
            }
        } else {
            println("MIDI not supported")
        }

    }

    fun connect(midiConnection: MidiConnection) {
        val m = context.getSystemService(Context.MIDI_SERVICE) as MidiManager
        midiConnection.connect(m)
        currentMidiConnection.value?.disconnect()
        currentMidiConnection.postValue(midiConnection)

    }

    fun disconnect(midiConnection: MidiConnection) {
        midiConnection.disconnect()
        currentMidiConnection.postValue(null)
    }

    /**
     * Sets a patch through the inputPort
     * @param patch Patch to be set
     */
    fun set_patch(patch: Patch) {
        // Set msb, lsb and pc
        if (currentMidiConnection.value == null) {
            return
        }
        currentMidiConnection.value!!.sendCcMessage(0, 0, patch.msb)
        currentMidiConnection.value!!.sendCcMessage(0, 32, patch.lsb)
        currentMidiConnection.value!!.sendPcMessage(0, patch.pc)
    }

    // TODO: Make in a generic way

    fun setVolume(volume: Int) {
        if (currentMidiConnection.value == null) {
            return
        }
        currentMidiConnection.value!!.sendCcMessage(0, 7, volume)
    }

    fun setPan(pan: Int) {
        if (currentMidiConnection.value == null) {
            return
        }
        currentMidiConnection.value!!.sendCcMessage(0, 10, pan)
    }

    fun setReverb(reverb: Int) {
        if (currentMidiConnection.value == null) {
            return
        }
        currentMidiConnection.value!!.sendCcMessage(0, 91, reverb)
    }

    fun setChorus(chorus: Int) {
        if (currentMidiConnection.value == null) {
            return
        }
        currentMidiConnection.value!!.sendCcMessage(0, 93, chorus)
    }
}
