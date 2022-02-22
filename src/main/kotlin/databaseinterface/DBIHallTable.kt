package databaseinterface

import auxiliary.CustomBoolean
import auxiliary.CustomDate
import domain.HallTable
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class DBIHallTable {
    val banco = "jdbc:mysql://localhost:3306/db000"

    var con: Connection? = null

    fun getSingleHallTable(id: Int): HallTable? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val hallTable = HallTable()
        hallTable.id = id
        val stmt = "select * from hall_table where id=?"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                ps.setInt(1, id)
                val resultSet = ps.executeQuery()
                if (resultSet.next()) {
                    auxLoad(hallTable, resultSet)
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return hallTable
    }

    fun setHallTableInUse(hallTableId: Int, saleId: Int) {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val stmt =
            "insert into hall_table(id, in_use, sale_id, production_print, delivered, opening_time, last_item_time, has_items) values (?, ?, ?, ?, ?, ?, ?, ?)"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                ps.setInt(1, hallTableId)
                ps.setInt(2, 1)
                ps.setInt(3, saleId)
                ps.setInt(4, 0)
                ps.setInt(5, 0)
                ps.setString(6, CustomDate().timeString)
                ps.setString(7, CustomDate().timeString)
                ps.setInt(8, 0)
                ps.execute()
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    fun getHallTableList(): ArrayList<HallTable>? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val list = ArrayList<HallTable>()
        val stmt = "select * from hall_table"
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