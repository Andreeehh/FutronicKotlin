import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.keyInputFilter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import auxiliary.CustomDate
import com.futronic.SDKHelper.*
import databaseinterface.DBIHallTable
import databaseinterface.DBISale
import domain.*
import domain.State
import java.awt.image.BufferedImage
import java.util.*
import kotlin.concurrent.schedule
import databaseinterface.DBI07 as clientDBI

@ExperimentalKeyInput
@Composable
fun biometry(user: MutableState<User?>, idTableCard: MutableState<String>) {
    lateinit var m_Operation: FutronicSdkBase
    lateinit var template: ByteArray
    var name by remember { mutableStateOf("") }
    val clientDBI = clientDBI()
    var clientList = clientDBI.getList()
    val clientListFull = clientList
    var clientId = 0
    var clientId2 = 0
    var clientNameSale by remember { mutableStateOf("") }
    var clientName by remember { mutableStateOf("") }
    var clientTell by remember { mutableStateOf("") }
    var clientDoc by remember { mutableStateOf("") }
    var foundClient by remember { mutableStateOf(true) }

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
                var result = FtrIdentifyResult()
                var candidates: Array<FtrIdentifyRecord?>? = clientDBI.getAllFingerprints()
                var nResult = (m_Operation as FutronicIdentification).Identification(candidates, result)
                if (nResult == FutronicSdkBase.RETCODE_OK) {
                    if (result.m_Index != -1) {
                        clientNameSale = clientDBI.getSingle("id", candidates!![result.m_Index]!!.clientId)!!.name.toString()
                        name = "ID Cliente: " + candidates!![result.m_Index]!!.clientId + "\nNome Cliente: $clientNameSale"
                        foundClient = true
                    } else {
                        foundClient = false
                        name = "Cliente não encontrado"
                    }
                }
            }
        }

        override fun OnEnrollmentComplete(bSuccess: Boolean, nResult: Int) {
            if (bSuccess) {
                if ((m_Operation as FutronicEnrollment).quality < 5) {
                    name = "Qualidade capturada muito baixa, tente novamente.\nQualidade: " + (m_Operation as FutronicEnrollment).quality
                    return
                }
                template = (m_Operation as FutronicEnrollment).template!!
                if (clientDBI.saveClientFingerprint(clientId2,template)){
                    name = "Sucesso. Qualidade da digita: "+ (m_Operation as FutronicEnrollment).quality
                }
            } else {
                name = FutronicSdkBase.SdkRetCode2Message(nResult)
            }
        }

    }

    fun writeFields(sale: Sale) {
        sale.subClient = SubClient()
        sale.openingDate = CustomDate().toString()
        sale.openingTime = CustomDate().timeString
        if (sale.type != 2 && !sale.isIfoodIndoor) {
            sale.hallTableId = 0
        }
        if (sale.type != 3) {
            sale.cardId = 0
        }
        if (sale.subClient == null) {
            sale.subClient = SubClient()
        }
        if (sale.client == null) {
            sale.client = Client()
        }
        if (sale.user == null) {
            sale.user = user.value
        }
        if (sale.discount > 0 && sale.webSaleType == 0) {
            sale.user = user.value
        }
        if (sale.employee == null) {
            sale.employee = Employee()
        }
        if (sale.employee2 == null) {
            sale.employee2 = Employee()
        }
        if (sale.neighborhood == null) {
            sale.neighborhood = Neighborhood()
        }
        if (sale.company == null) {
            sale.company = Company()
        }
        if (sale.type != 1 && sale.type != 4 && !sale.isDelivered) {
            sale.deliverDate = CustomDate().toString()
            sale.deliverTime = CustomDate().timeString
        }
        sale.isPendentItem = false
        if (sale.hasPendentItem()) {
            sale.isPendentItem = true
        }
        sale.isPendent = false
        if (sale.pendentValue > 0) {
            sale.isPendent = true
        }
        if (!sale.isPayed) {
            sale.paymentDate = "01/01/1990"
            sale.paymentTime = "12:00"
        }
    }

    fun openCardHallTable(type: Int) {
        if (DBIHallTable().getSingleHallTable(idTableCard.value.toInt())!!.isInUse) {
            name = if (type == 1)
                "Mesa em uso"
            else
                "Comanda em uso"
            return
        }
        var id = DBISale().getUpdateNextId()
        val newSale = Sale()
        newSale.id = id
        newSale.type = 2
        newSale.hallTableId = idTableCard.value.toInt()
        newSale.client = clientDBI.getSingle("id", clientId)
        newSale.client!!.id = clientId
        writeFields(newSale)
        if (DBISale().add(newSale, 0)) {
            name = if (type == 1)
                "Mesa aberta"
            else
                "Comanda aberta"
            DBIHallTable().setHallTableInUse(idTableCard.value.toInt(), newSale.id)
        } else {
            name = if (type == 1)
                "Problema ao abrir mesa"
            else
                "Problema ao abrir comanda"
        }
    }

    MaterialTheme {
        var expanded by remember { mutableStateOf(false) }
        var abertura by remember { mutableStateOf(true) }
        Column(modifier = Modifier.width(250.dp)) {
            Button(
                onClick = {
                    foundClient = true
                    abertura = false
                    name = ""
                },
                modifier = Modifier.offset(30.dp,50.dp)
            )
            {
                Text("Cadastrar Cliente")
            }
            Button(
                onClick = {
                    abertura = true
                    name = ""
                    clientName = ""
                    clientTell = ""
                    clientDoc = ""
                    clientId2 = 0
                },
                modifier = Modifier.offset(30.dp,80.dp)
            )
            {
                Text("Abertura mesa/comanda")
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(0.dp, 200.dp)
                    .wrapContentSize(Alignment.TopStart)
            ) {
                if (abertura) {
                    TextField(
                        value = clientNameSale,
                        onValueChange = {
                            clientNameSale = it
                            Timer().schedule(800) {
                                if (clientNameSale != "") {
                                    clientList = if (clientNameSale[0].toString().matches(regex = "^\\(".toRegex())) {
                                        clientDBI.getFiltredList(2, clientNameSale)
                                    } else {
                                        if (clientNameSale.matches(regex = "^[0-9]+".toRegex())) {
                                            clientDBI.getFiltredList(3, clientNameSale)
                                        } else {
                                            clientDBI.getFiltredList(1, clientNameSale)
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
                        label = { Text("Cliente") },
                        placeholder = { Text("Aperte enter para abrir a lista") },
                        modifier = Modifier
                            .keyInputFilter {
                                if (it.key == Key.Enter) {
                                    if (clientNameSale != "") {
                                        clientList = if (clientNameSale[0].toString().matches(regex = "^\\(".toRegex())) {
                                            clientDBI.getFiltredList(2, clientNameSale)
                                        } else {
                                            if (clientNameSale.matches(regex = "^[0-9]+".toRegex())) {
                                                clientDBI.getFiltredList(3, clientNameSale)
                                            } else {
                                                clientDBI.getFiltredList(1, clientNameSale)
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
                    IconButton(onClick = { expanded = true }, modifier = Modifier.offset(350.dp, 0.dp)) {
                        Icon(Icons.Default.AccountCircle)
                    }

                    TextField(
                        value = idTableCard.value,
                        onValueChange = { idTableCard.value = it },
                        label = { Text("Nº Mesa/Comanda") },
                        placeholder = { Text("insira o Nº Mesa/Comanda") },
                        modifier = Modifier.offset(0.dp, 70.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
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
                                    clientNameSale = client.name.toString()
                                }
                            ) {
                                Text(text = client.name.toString() + client.cellPhone.toString())
                            }
                        }
                    }
                } else {
                    TextField(
                        value = clientName,
                        onValueChange = {
                            clientName = it
                            Timer().schedule(800) {
                                if (!(clientName == "")) {
                                    if (clientName[0].toString().matches(regex = "^\\(".toRegex())) {
                                        clientList = clientDBI.getFiltredList(2, clientName)
                                    } else {
                                        if (clientName.matches(regex = "^[0-9]+".toRegex())) {
                                            clientList = clientDBI.getFiltredList(3, clientName)
                                        } else {
                                            clientList = clientDBI.getFiltredList(1, clientName)
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
                        label = { Text("Cliente") },
                        placeholder = { Text("Aperte enter para abrir a lista") },
                        modifier = Modifier
                            .keyInputFilter {
                                if (it.key == Key.Enter) {
                                    if (clientName != "") {
                                        clientList = if (clientName[0].toString().matches(regex = "^\\(".toRegex())) {
                                            clientDBI.getFiltredList(2, clientName)
                                        } else {
                                            if (clientName.matches(regex = "^[0-9]+".toRegex())) {
                                                clientDBI.getFiltredList(3, clientName)
                                            } else {
                                                clientDBI.getFiltredList(1, clientName)
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
                                    clientId2 = client.id
                                    name = "CLiente ${client.name} selecionado, ID: $clientId2"
                                    expanded = false
                                    clientName = client.name.toString()
                                    clientDoc = client.idDocNumber2.toString()
                                    clientTell = client.cellPhone.toString()
                                }
                            ) {
                                Text(text = client.name.toString() + client.cellPhone.toString())
                            }
                        }
                    }
                    TextField(
                        value = clientTell,
                        onValueChange = {clientTell = it},
                        label = { Text("Nº Celular") },
                        placeholder = { Text("(XX) XXXXX-XXXX") },
                        singleLine = true,
                        modifier = Modifier.offset(0.dp, 100.dp)
                    )
                    TextField(
                        value = clientDoc,
                        onValueChange = {clientDoc = it},
                        label = { Text("Nº CPF") },
                        placeholder = { Text("XXX.XXX.XXX-XX") },
                        singleLine = true,
                        modifier = Modifier.offset(0.dp, 200.dp)
                    )
                }
            }
            Text(
                name, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(0.dp, 600.dp)
            )
            if (abertura){
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
                Button(
                    onClick = { openCardHallTable(1) }, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset((-75).dp, 650.dp)
                )
                {
                    Text("Abrir Mesa")
                }
                Button(
                    onClick = { openCardHallTable(2) }, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(75.dp, 615.dp)
                )
                {
                    Text("Abrir Comanda")
                }
            } else {
                Button(
                    onClick = {
                        if (clientId2 != 0){
                            name = "Cliente já está cadastrado"
                            return@Button
                        }
                        var client = Client()
                        client.id = clientDBI.getUpdateNextId()
                        client.name = clientName
                        client.idDocNumber2 = clientDoc
                        client.cellPhone2 = clientTell
                        client.register = CustomDate().toString()
                        if (clientDBI.add(client)){
                            name = "Cliente cadastrado com sucesso"
                            clientId2 = client.id
                        } else {
                            name = "Problemas ao cadastrar o cliente"
                        }
                    }, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(0.dp, 500.dp)
                )
                {
                    Text("Cadastrar Cliente")
                }
                if (!foundClient){
                    Button(
                        onClick = {
                            if (clientId2 == 0) {
                                name = "Cadastre/Selecione um cliente"
                            } else {
                                try {
                                    m_Operation = FutronicEnrollment()
                                    m_Operation.fakeDetection = true
                                    m_Operation.fFDControl = true
                                    m_Operation.fARN = 166
                                    m_Operation.fastMode = true
                                    (m_Operation as FutronicEnrollment).mIOTControlOff = true
                                    (m_Operation as FutronicEnrollment).maxModels = 4
                                    m_Operation.version = VersionCompatible.ftr_version_current
                                } catch (futronicException: FutronicException) {
                                    futronicException.printStackTrace()
                                }
                                (m_Operation as FutronicEnrollment).Enrollment(BiometryFun())
                            }
                        }, modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .offset(0.dp, 600.dp)
                    )
                    {
                        Text("Cadastrar Biometria")
                    }
                } else {
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
                            .offset(0.dp, 600.dp)
                    )
                    {
                        Text("Verificar Biometria")
                    }
                }

            }
        }

    }
}