import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.keyInputFilter
import androidx.compose.ui.unit.dp
import com.futronic.SDKHelper.*
import domain.Client
import org.jetbrains.skija.Color
import java.awt.image.BufferedImage
import java.util.*
import databaseinterface.DBI07 as clientDBI
import databaseinterface.DBICard as cardDBI
import kotlin.concurrent.schedule
import domain.Card
import cardInterface as cardInterface

@ExperimentalKeyInput
@Composable
fun Biometry () {
    lateinit var m_Operation: FutronicSdkBase
    lateinit var template: ByteArray
    var name by remember { mutableStateOf("") }
    val clientDBI = clientDBI()
    var clientList = clientDBI.getList()
    val clientListFull = clientList
    var clientId = 0
    var value by remember { mutableStateOf("") }
    class BiometryFun:IEnrollmentCallBack, IIdentificationCallBack {

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
            if (bSuccess){
                var result: FtrIdentifyResult = FtrIdentifyResult()
                var candidates: Array<FtrIdentifyRecord?>? = clientDBI.getAllFingerprints()
                var nResult = (m_Operation as FutronicIdentification).Identification(candidates, result)
                if (nResult == FutronicSdkBase.RETCODE_OK) {
                    if (result.m_Index != -1) {
                        name = "Client ID: " + candidates!![result.m_Index]!!.clientId
                        value = clientDBI.getSingle("id", candidates!![result.m_Index]!!.clientId)!!.name.toString()
                    } else {
                        name = "Cliente não encontrado"
                    }
                }
            }
        }

        override fun OnEnrollmentComplete(bSuccess: Boolean, nResult: Int) {
            if (bSuccess) {
                if ((m_Operation as FutronicEnrollment).quality < 5) {
                    name = "Qualidade capturada muito baixa, tente novamente."
                    name = "Qualidade: " + (m_Operation as FutronicEnrollment).quality
                    return
                }
                template = (m_Operation as FutronicEnrollment).template!!
                if (clientDBI.saveClientFingerprint(clientId,template)){
                    name = "Sucesso. Qualidade da digita: "+ (m_Operation as FutronicEnrollment).quality
                }
            } else {
                name = FutronicSdkBase.SdkRetCode2Message(nResult)
            }
        }

    }

    MaterialTheme {
        var expanded by remember { mutableStateOf(false) }
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(0.dp, 200.dp)
                .wrapContentSize(Alignment.TopStart)) {
                TextField(
                    value = value,
                    onValueChange = {
                        value = it
                        Timer().schedule(800){
                            if (!(value == "")){
                                if(value[0].toString().matches(regex = "^\\(".toRegex())) {
                                    clientList = clientDBI.getFiltredList(2, value)
                                } else {
                                    if (value.matches(regex = "^[0-9]+".toRegex())) {
                                        clientList = clientDBI.getFiltredList(3, value)
                                    } else{
                                        clientList = clientDBI.getFiltredList(1, value)
                                    }
                                }
                                expanded = true
                                true
                            } else {
                                expanded = false
                                clientList = clientListFull
                            }
                        }
                        },
                    label = { Text("Cliente")},
                    placeholder = { Text("Aperte enter ou para seta para baixo para abrir a lista")},
                    modifier = Modifier
                        .keyInputFilter {
                            if ( it.key == Key.Enter || it.key == Key.DPadDown) {
                                if (!(value == "")){
                                    if(value[0].toString().matches(regex = "^\\(".toRegex())) {
                                        clientList = clientDBI.getFiltredList(2, value)
                                    } else {
                                        if (value.matches(regex = "^[0-9]+".toRegex())) {
                                                clientList = clientDBI.getFiltredList(3, value)
                                            } else{
                                            clientList = clientDBI.getFiltredList(1, value)
                                        }
                                    }
                                    expanded = true
                                    true
                                } else {
                                    clientList = clientListFull
                                    expanded = true
                                    true
                                }
                            } else {
                                false
                            }
                        },
                    singleLine = true
                )
                IconButton(onClick = { expanded = true }, modifier = Modifier.offset(350.dp,0.dp)) {
                    Icon(Icons.Default.AccountCircle)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    toggle = {
                        Text(
                            text = "",
                            modifier = Modifier.clickable { expanded = true }
                                .clickable(onClick = { expanded = true })
                        )
                    }
                ) {
                    clientList!!.forEach { client ->
                        DropdownMenuItem(
                            onClick = {
                                clientId = client.id
                                name = "CLiente ${client.name} selecionado, ID: ${client.id}"
                                expanded = false
                                value = client.name.toString()
                            }
                        ) {
                            Text(text = client.name.toString() + client.cellPhone.toString())
                        }
                    }
                }
            }

            Button(onClick = {
                if (clientId == 0){
                    name = "Selecione um cliente"
                }else {
                    try {

                        m_Operation = FutronicEnrollment()
                        m_Operation.fakeDetection = true
                        m_Operation.fFDControl = true
                        m_Operation.fARN = 166
                        m_Operation.fastMode = true
                        (m_Operation as FutronicEnrollment).mIOTControlOff = true
                        (m_Operation as FutronicEnrollment).maxModels = 4
                        m_Operation.version = VersionCompatible.ftr_version_current
                    }catch (futronicException: FutronicException){
                        futronicException.printStackTrace()
                    }
                    (m_Operation as FutronicEnrollment).Enrollment(BiometryFun())
                }
            },modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset((-75).dp, 600.dp) )
            {
                Text("Cadastrar")
            }
            Text(name, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(0.dp, 600.dp))
            Button(onClick = {
                try {
                    m_Operation = FutronicIdentification()
                    m_Operation.fakeDetection = true
                    m_Operation.fFDControl = true
                    m_Operation.fARN = 166
                    m_Operation.fastMode = false
                    m_Operation.version = VersionCompatible.ftr_version_current
                } catch (futronicException: FutronicException){
                    futronicException.printStackTrace()
                }
                (m_Operation as FutronicIdentification).GetBaseTemplate(BiometryFun())
            },modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(75.dp, 543.dp))
            {
                Text("Buscar")
            }
        }

    }
}