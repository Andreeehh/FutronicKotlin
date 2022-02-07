package databaseinterface

import auxiliary.CustomBoolean
import com.futronic.SDKHelper.FtrIdentifyRecord
import domain.*
import java.nio.ByteBuffer
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import javax.sql.rowset.serial.SerialBlob

class DBI07 {
    val banco = "jdbc:mysql://localhost:3306/db000"

    var con: Connection? = null


    fun getSingle(columnName: String, id: Int): Client? {
        con = DriverManager.getConnection(banco,"root","1234")
        var client: Client? = null
        val stmt = "select * from client where $columnName = ?"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                ps.setInt(1, id)
                val resultSet = ps.executeQuery()
                if (resultSet.next()) {
                    client = Client()
                    this.auxLoad(client!!, resultSet)
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return client
    }

    fun saveClientFingerprint(clientId: Int, template: ByteArray?): Boolean {

        val stmt = "insert into client_fingerprint (client_id, fingerprint) VALUES (?, ?)"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                ps.setInt(1, clientId)
                ps.setBlob(2, SerialBlob(template))
                ps.execute()
            }
        } catch (ex: SQLException) {
            println("Problema a cadastrar o cliente")
            ex.printStackTrace()
            return false
        }
        return true
    }

    fun getList(): Vector<Client>? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val list = Vector<Client>()
        val stmt = "select * from client"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                val resultSet = ps.executeQuery()
                while (resultSet.next()) {
                    val client = Client()
                    auxLoad(client, resultSet)
                    list.add(client)
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return list
    }

    fun getFiltredList(type: Int, filter: String): Vector<Client>? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val list = Vector<Client>()
        val string = when (type) {
            1 -> "name"
            2 -> "phone"
            3 -> "id_doc_number2"
            else -> "invalid"
        }
        val stmt = "select * from client where $string LIKE '%$filter%'"
        val stmt2 = "select * from client where cell_phone LIKE '%$filter%'"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                val resultSet = ps.executeQuery()
                while (resultSet.next()) {
                    val client = Client()
                    auxLoad(client, resultSet)
                    list.add(client)
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        if (type == 2){
            try {
                con!!.prepareStatement(stmt2).use { ps ->
                    val resultSet = ps.executeQuery()
                    while (resultSet.next()) {
                        val client = Client()
                        auxLoad(client, resultSet)
                        list.add(client)
                    }
                }
            } catch (ex: SQLException) {
                ex.printStackTrace()
            }
        }
        return list
    }


    fun getAllFingerprints(): Array<FtrIdentifyRecord?>? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        var fingerprintsCount = 0
        val stmtCount = "select count(client_id) as count from client_fingerprint;"
        try {
            con!!.prepareStatement(stmtCount).use { ps ->
                val resultSet = ps.executeQuery()
                if (resultSet.next()) {
                    fingerprintsCount = resultSet.getInt("count")
                }
            }
        } catch (ex: SQLException) {
            println(ex.message)
        }
        val fingerprintList = arrayOfNulls<FtrIdentifyRecord>(fingerprintsCount)
        val stmt = "select * from client_fingerprint;"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                val resultSet = ps.executeQuery()
                var count = 0
                while (resultSet.next()) {
                    val client = FtrIdentifyRecord()
                    client.clientId = resultSet.getInt("client_id")
                    client.m_KeyValue = ByteBuffer.allocate(4).putInt(client.clientId).array()
                    val fingerprint = resultSet.getBlob("fingerprint")
                    val blobLength = fingerprint.length().toInt()
                    client.m_Template = SerialBlob(fingerprint).getBytes(1, blobLength)
                    fingerprintList[count] = client
                    count++
                }
            }
        } catch (ex: SQLException) {
            println(ex.message)
        }
        return fingerprintList
    }

    private fun auxLoad(client: Client, resultSet: ResultSet) {
        try {
            client.id = resultSet.getInt("id")
            client.name = resultSet.getString("name")
            client.docName = resultSet.getString("doc_name")
            client.ownerName = resultSet.getString("owner_name")
            client.type1 = resultSet.getInt("type1")
            client.type2 = resultSet.getInt("type2")
            client.gender = resultSet.getInt("gender")
            client.occupation = resultSet.getString("occupation")
            client.birthday = this.formatDateDBToApp(resultSet.getString("birthday"))
            client.phone = resultSet.getString("phone")
            client.cellPhone = resultSet.getString("cell_phone")
            client.phone2 = resultSet.getString("phone2")
            client.cellPhone2 = resultSet.getString("cell_phone2")
            client.email = resultSet.getString("email")
            client.isSendNotifications = CustomBoolean(resultSet.getInt("send_notifications")).boolean
            /*if (resultSet.getInt("state_id") == 0) {
                client.setState(State())
            } else {
                client.setState(this.dataStructures.getByIdState(resultSet.getInt("state_id")))
            }*/
            client.setState(State())
            /*
            if (resultSet.getInt("city_id") == 0) {
                client.setCity(City())
            } else {
                client.setCity(this.dataStructures.getByIdCity(resultSet.getInt("city_id")))
            }*/
            client.setCity(City())
            client.streetName = resultSet.getString("street_name")
            client.streetNumber = resultSet.getString("street_number")
            client.zipcode = resultSet.getString("zipcode")
            /*if (resultSet.getInt("neighborhood_id") == 0) {
                client.setNeighborhood(Neighborhood())
            } else {
                client.setNeighborhood(this.dataStructures.getById14(resultSet.getInt("neighborhood_id")))
            }*/
            client.setNeighborhood(Neighborhood())
            client.complement = resultSet.getString("complement")
            client.idDocNumber = resultSet.getString("id_doc_number")
            client.idDocNumber2 = resultSet.getString("id_doc_number2")
            client.idDocNumber3 = resultSet.getString("id_doc_number3")
            client.idDocNumber4 = resultSet.getString("id_doc_number4")
            client.idDocNumber5 = resultSet.getString("id_doc_number5")
            /*if (resultSet.getInt("company_id") == 0) {
                client.setCompany(Company())
            } else {
                client.setCompany(this.dataStructures.getById21(resultSet.getInt("company_id")))
            }*/
            client.setCompany(Company())
            client.notes = resultSet.getString("notes")
            client.isAccount = CustomBoolean(resultSet.getInt("locked")).boolean
            client.coordinates = resultSet.getString("coordinates")
            client.expirationDay = resultSet.getInt("expiration_day")
            client.expirationMonthOffset = resultSet.getInt("expiration_month_offset")
            client.clientCreditLimit = resultSet.getInt("client_credit_limit")
            client.companyCreditLimit = resultSet.getInt("company_credit_limit")
            client.standardValue = resultSet.getInt("value1")
            client.taxes = resultSet.getInt("value2")
            client.credits = resultSet.getInt("value3")
            client.value4 = resultSet.getInt("value4")
            client.register = this.formatDateDBToApp(resultSet.getString("register"))
            client.payments = resultSet.getString("payments")
            client.externalId = resultSet.getString("external_id")
            /*if (resultSet.getInt("employee_id") == 0) {
                client.setEmployee(Employee())
            } else {
                client.setEmployee(this.dataStructures.getById04(resultSet.getInt("employee_id")))
            }*/
            client.setEmployee(Employee())
            client.isActive = CustomBoolean(resultSet.getInt("active")).boolean
        } catch (ex: SQLException) {
            println(ex.message)
        }
    }
    fun formatDateDBToApp(date: String?): String? {
        if (date == null || date == "") {
            return ""
        }
        if (date.contains("-")) {
            val fields = date.split("-").toTypedArray()
            return fields[2] + "/" + fields[1] + "/" + fields[0]
        }
        return date.substring(6) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4)
    }
}