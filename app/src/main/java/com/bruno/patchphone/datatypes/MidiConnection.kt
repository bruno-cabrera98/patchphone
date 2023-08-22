package com.bruno.patchphone.datatypes

import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiManager
import android.media.midi.MidiOutputPort
import android.media.midi.MidiReceiver
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

enum class MidiConnectionStatus {
    CONNECTED,
    DISCONNECTED,
    CONNECTING
}

class MidiConnection {
    constructor(midiDeviceInfo: MidiDeviceInfo) {
        this.midiDeviceInfo = midiDeviceInfo
        this.name = midiDeviceInfo.properties.getString(MidiDeviceInfo.PROPERTY_NAME).toString()
        this.status.value = MidiConnectionStatus.DISCONNECTED
    }
    constructor(name: String, status: MidiConnectionStatus) {
        this.name = name
        this.status.value = status
    }

    var name: String = ""
    private var midiDeviceInfo: MidiDeviceInfo? = null

    private var inputPort : MidiReceiver? = null
    private var outputPort : MidiOutputPort? = null

    var status: MutableState<MidiConnectionStatus> = mutableStateOf(MidiConnectionStatus.DISCONNECTED)

    fun sendCcMessage(channel: Int, control: Int, value: Int) {
        val message = ByteArray(3)
        message[0] = (0xB0 + channel).toByte()
        message[1] = control.toByte()
        message[2] = value.toByte()
        inputPort?.send(message, 0, 3)
    }

    fun sendPcMessage(channel: Int, pc: Int) {
        val message = ByteArray(2)
        message[0] = (0xC0 + channel).toByte()
        message[1] = pc.toByte()
        inputPort?.send(message, 0, 2)
    }

    fun connect(m: MidiManager) {
        status.value= MidiConnectionStatus.CONNECTING
        m.openDevice(midiDeviceInfo!!, { device ->
            inputPort = device.openInputPort(0)
            outputPort = device.openOutputPort(0)
            status.value = MidiConnectionStatus.CONNECTED
        }, null)
    }

    fun disconnect() {
        inputPort = null
        outputPort?.close()
        status.value = MidiConnectionStatus.DISCONNECTED
    }

}