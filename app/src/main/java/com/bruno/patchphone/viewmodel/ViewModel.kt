import android.media.midi.MidiDeviceInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@Composable
fun MidiDevices(devices: List<MidiDeviceInfo>) {
    val devices = devices ?: emptyList()

    Column() {
        devices.forEach {
            Button(onClick = { /*TODO*/ }) {
                Text(text = it.properties.getString(MidiDeviceInfo.PROPERTY_NAME) ?: "No name")
            }
        }
    }
}

@Preview
@Composable
fun TextList(
    @PreviewParameter(TextProvider::class) texts: List<String>
) {
    Column() {
        texts.forEach {
            Button(onClick = { /*TODO*/ }) {
                Text(text = it)
            }
        }
    }
}

class TextProvider : PreviewParameterProvider<List<String>> {
    override val values: Sequence<List<String>>
        get() = sequenceOf(listOf("Hello", "World"))
}




class MyViewModel : ViewModel() {
    val myText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}
