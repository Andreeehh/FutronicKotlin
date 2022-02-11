package databaseinterface

import auxiliary.CustomBoolean
import domain.Card
import domain.HallTable
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class DBIHallTable {
    val banco = "jdbc:mysql://localhost:3306/db000"

    var con: Connection? = null

    fun getHallTableList(): ArrayList<HallTable>? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val list = ArrayList<HallTable>()
        val stmt = "select * from card"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                val resultSet = ps.executeQuery()
                while (resultSet.next()) {
                    val hallTable = HallTable()
                    this.auxLoad(hallTable, resultSet)
                    list.add(hallTable)
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return list
    }
    private fun auxLoad(hallTable: HallTable, resultSet: ResultSet) {
        try {
            hallTable.id = resultSet.getInt("id")
            hallTable.isInUse = CustomBoolean(resultSet.getInt("in_use")).boolean
            hallTable.saleId = resultSet.getInt("sale_id")
            hallTable.isProductionPrint = CustomBoolean(resultSet.getInt("production_print")).boolean
            hallTable.isDelivered = CustomBoolean(resultSet.getInt("delivered")).boolean
            hallTable.openingTime = resultSet.getString("opening_time")
            hallTable.lastItemTime = resultSet.getString("last_item_time")
            hallTable.setHasItems(CustomBoolean(resultSet.getInt("has_items")).boolean)
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }
}