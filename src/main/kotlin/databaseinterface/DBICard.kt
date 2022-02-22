package databaseinterface

import auxiliary.CustomBoolean
import auxiliary.CustomDate
import domain.Card
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class DBICard {
    val banco = "jdbc:mysql://localhost:3306/db000"

    var con: Connection? = null

    fun getCardList(): ArrayList<Card>? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val list = ArrayList<Card>()
        val stmt = "select * from card"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                val resultSet = ps.executeQuery()
                while (resultSet.next()) {
                    val card = Card()
                    this.auxLoad(card, resultSet)
                    list.add(card)
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return list
    }

    fun getSingleCard(id: Int): Card? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val card = Card()
        card.id = id
        val stmt = "select * from card where id=?"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                ps.setInt(1, id)
                val resultSet = ps.executeQuery()
                if (resultSet.next()) {
                    auxLoad(card, resultSet)
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return card
    }

    fun setCardInUse(cardId: Int, saleId: Int, hallTableId: Int) {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val stmt =
            "insert into card(id, in_use, sale_id, production_print, delivered, opening_time, last_item_time, has_items, hall_table_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                ps.setInt(1, cardId)
                ps.setInt(2, 1)
                ps.setInt(3, saleId)
                ps.setInt(4, 0)
                ps.setInt(5, 0)
                ps.setString(6, CustomDate().timeString)
                ps.setString(7, CustomDate().timeString)
                ps.setInt(8, 0)
                ps.setInt(9, hallTableId)
                ps.execute()
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    private fun auxLoad(card: Card, resultSet: ResultSet) {
        try {
            card.id = resultSet.getInt("id")
            card.isInUse = CustomBoolean(resultSet.getInt("in_use")).boolean
            card.saleId = resultSet.getInt("sale_id")
            card.isProductionPrint = CustomBoolean(resultSet.getInt("production_print")).boolean
            card.isDelivered = CustomBoolean(resultSet.getInt("delivered")).boolean
            card.openingTime = resultSet.getString("opening_time")
            card.lastItemTime = resultSet.getString("last_item_time")
            card.setHasItems(CustomBoolean(resultSet.getInt("has_items")).boolean)
            card.hallTableId = resultSet.getInt("hall_table_id")
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }
}