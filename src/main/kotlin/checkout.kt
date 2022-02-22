import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import auxiliary.CustomDate
import com.futronic.SDKHelper.*
import databaseinterface.DBI07
import databaseinterface.DBISale
import domain.Sale
import java.awt.image.BufferedImage
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.*


@Composable
fun checkout() {
    lateinit var m_Operation: FutronicSdkBase
    lateinit var template: ByteArray
    var name by remember { mutableStateOf("") }
    var clearCheckout by remember { mutableStateOf("") }
    val clientDBI = DBI07()
    var clientId = 0
    val saleDBI = DBISale()
    var saleList = ArrayList<Sale>()
    var showIcon by remember { mutableStateOf(false) }
    var clearIcon by remember { mutableStateOf(false) }

    class BiometryFun : IEnrollmentCallBack, IIdentificationCallBack {

        override fun OnPutOn(Progress: FTR_PROGRESS?) {
            name = "Put finger into device, please ..."
        }

        override fun OnTakeOff(Progress: FTR_PROGRESS?) {
            name = "Take off finger from device, please ..."
        }

        override fun UpdateScreenImage(Bitmap: BufferedImage?) {
            name = "atualizando imagem"
        }

        override fun OnFakeSource(Progress: FTR_PROGRESS?): Boolean {
            name = "Fake source detected. Do you want continue process?"
            return false
        }

        override fun OnGetBaseTemplateComplete(bSuccess: Boolean, nResult: Int) {
            if (bSuccess) {
                val cal = Calendar.getInstance()
                val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                cal.add(Calendar.DATE, -1)
                saleDBI.loadList(saleList, dateFormat.format(cal.time).toString(), CustomDate().toString(), false)
                var result = FtrIdentifyResult()
                var candidates: Array<FtrIdentifyRecord?>? = clientDBI.getAllFingerprints()
                var nResult = (m_Operation as FutronicIdentification).Identification(candidates, result)
                if (nResult == FutronicSdkBase.RETCODE_OK) {
                    if (result.m_Index != -1) {
                        name = "Client ID: " + candidates!![result.m_Index]!!.clientId +
                                "\nNome: " + DBI07().getSingle("id",candidates!![result.m_Index]!!.clientId)!!.name
                        for (sale in saleList) {
                            if (sale.client!!.id != candidates!![result.m_Index]!!.clientId) {
                                continue
                            }
                            showIcon = true
                            if (sale.isPayed) {
                                clearIcon = true
                                clearCheckout = "Liberado"
                            } else {
                                clearIcon = false
                                clearCheckout = "Existem Vendas em aberto"
                                if (sale.cardId != 0) {
                                    clearCheckout += "\nComanda: ${sale.cardId}"
                                }
                                if (sale.hallTableId != 0) {
                                    clearCheckout += "\nMesa ${sale.hallTableId}"
                                }
                            }
                        }
                    } else {
                        name = "Cliente não encontrado"
                    }
                }
            }
        }

        override fun OnEnrollmentComplete(bSuccess: Boolean, nResult: Int) {
            if (bSuccess) {
                if ((m_Operation as FutronicEnrollment).quality < 5) {
                    name =
                        "Qualidade capturada muito baixa, tente novamente.\nQualidade: " + (m_Operation as FutronicEnrollment).quality
                    return
                }
                template = (m_Operation as FutronicEnrollment).template!!
                if (clientDBI.saveClientFingerprint(clientId, template)) {
                    name = "Sucesso. Qualidade da digita: " + (m_Operation as FutronicEnrollment).quality
                }
            } else {
                name = FutronicSdkBase.SdkRetCode2Message(nResult)
            }
        }

    }
    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = {
                    try {
                        m_Operation = FutronicIdentification()
                        m_Operation.fakeDetection = true
                        m_Operation.fFDControl = true
                        m_Operation.fARN = 166
                        m_Operation.fastMode = false
                        m_Operation.version = VersionCompatible.ftr_version_current
                    } catch (futronicException: FutronicException) {
                        futronicException.printStackTrace()
                    }
                    (m_Operation as FutronicIdentification).GetBaseTemplate(BiometryFun())
                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(0.dp, 545.dp)
            )
            {
                Text("Buscar por biometria")
            }
            Text(
                name, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(0.dp, 600.dp)
            )
            Text(
                clearCheckout, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(0.dp, 650.dp)
            )
            if (showIcon) {
                if (clearIcon){
                    Icon(Icons.Default.CheckCircle,tint = androidx.compose.ui.graphics.Color.Green, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(100.dp, 612.dp))
                } else {
                    Icon(Icons.Default.CheckCircle,tint = androidx.compose.ui.graphics.Color.Red, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(100.dp, 612.dp))
                }

            }

        }
    }
}