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
    var textButton = "Olá"
    lateinit var m_Operation: FutronicSdkBase
    lateinit var template: ByteArray

    @Composable
    @Preview
    fun teste () {
        //var text by remember { mutableStateOf("TESTE, World!") }
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
                (m_Operation as FutronicEnrollment).Enrollment(this)
            })
            {
                Text(textButton)
            }
        }
    }


    override fun OnPutOn(Progress: FTR_PROGRESS?) {
        textButton = "Put finger into device, please ..."
    }

    override fun OnTakeOff(Progress: FTR_PROGRESS?) {
        textButton = "Take off finger from device, please ..."
    }

    override fun UpdateScreenImage(Bitmap: BufferedImage?) {
        textButton = "atualizando imagem"
    }

    override fun OnFakeSource(Progress: FTR_PROGRESS?): Boolean {
        textButton = "Fake source detected. Do you want continue process?"
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
                textButton = "Qualidade capturada muito baixa, tente novamente."
                textButton = "Qualidade: " + (m_Operation as FutronicEnrollment).quality
                return
            }
            this.template = (m_Operation as FutronicEnrollment).template!!
            textButton = "Sucesso. Qualidade da digita: "+ (m_Operation as FutronicEnrollment).quality
        } else {
            textButton = FutronicSdkBase.SdkRetCode2Message(nResult)
        }
    }

    fun getAllFingerprints() {

    }

}
