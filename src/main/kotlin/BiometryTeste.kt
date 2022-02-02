import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.futronic.SDKHelper.*
import java.awt.Button
import java.awt.image.BufferedImage

class BiometryTeste:IEnrollmentCallBack, IIdentificationCallBack {
    var textButton = mutableStateOf("Hello" )
    var m_Operation: FutronicSdkBase = TODO()
    var template: ByteArray

    @Composable
    @Preview
    fun teste () {
        //var text by remember { mutableStateOf("TESTE, World!") }
        DesktopMaterialTheme {
            Button(onClick = {
                m_Operation = FutronicEnrollment()
                m_Operation.fakeDetection = true
                m_Operation.ffdControl = true
                m_Operation.farn = 166
                m_Operation.fastMode = true
                (m_Operation as FutronicEnrollment).miotControlOff = true
                (m_Operation as FutronicEnrollment).maxModels = 4
                m_Operation.version = VersionCompatible.ftr_version_current
                (m_Operation as FutronicEnrollment).Enrollment(this)
            })
            {
                Text(textButton.toString())
            }
        }
    }


    override fun OnPutOn(Progress: FTR_PROGRESS?) {
        textButton = mutableStateOf("Put finger into device, please ..." )
    }

    override fun OnTakeOff(Progress: FTR_PROGRESS?) {
        textButton = mutableStateOf("Take off finger from device, please ..." )
    }

    override fun UpdateScreenImage(Bitmap: BufferedImage?) {
        textButton = mutableStateOf("atualizando imagem")
    }

    override fun OnFakeSource(Progress: FTR_PROGRESS?): Boolean {
        textButton = mutableStateOf("Fake source detected. Do you want continue process?")
        return false
    }

    override fun OnGetBaseTemplateComplete(bSuccess: Boolean, nResult: Int) {
        if (bSuccess){
            var result: FtrIdentifyResult
            var candidates: FtrIdentifyRecord
        }
    }

    override fun OnEnrollmentComplete(bSuccess: Boolean, nResult: Int) {
        if (bSuccess) {
            if ((m_Operation as FutronicEnrollment).quality < 5) {
                textButton = mutableStateOf("Qualidade capturada muito baixa, tente novamente.")
                textButton = mutableStateOf("Qualidade: " + (m_Operation as FutronicEnrollment).quality)
                return
            }
            this.template = (m_Operation as FutronicEnrollment).template
            textButton = mutableStateOf("Sucesso. Qualidade da digita: "+ (m_Operation as FutronicEnrollment).quality)
        } else {
            textButton = mutableStateOf(FutronicSdkBase.SdkRetCode2Message(nResult))
        }
    }

    fun getAllFingerprints() {

    }

}
