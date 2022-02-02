import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.futronic.SDKHelper.FutronicEnrollment
import com.futronic.SDKHelper.FutronicSdkBase
import com.futronic.SDKHelper.VersionCompatible

@Composable
@Preview
fun teste () {
    //var text by remember { mutableStateOf("TESTE, World!") }
    var m_Operation: FutronicSdkBase


    DesktopMaterialTheme {
        Button(onClick = {
            m_Operation = FutronicEnrollment()
            m_Operation.fakeDetection = true
            m_Operation.fFDControl = true
            m_Operation.fARN = 166
            m_Operation.fastMode = true
            (m_Operation as FutronicEnrollment).mIOTControlOff = true
            (m_Operation as FutronicEnrollment).maxModels = 4
            m_Operation.version = VersionCompatible.ftr_version_current
        }) {
            Text("Hello")
        }
    }
}