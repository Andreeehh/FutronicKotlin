package databaseinterface

import auxiliary.CustomBoolean
import domain.Card
import domain.User
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class DBIUser {
    val banco = "jdbc:mysql://localhost:3306/db000"

    var con: Connection? = null

    fun getList(): Vector<User>? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val list = Vector<User>()
        val stmt = "select * from app_user where id <> 1"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                val resultSet = ps.executeQuery()
                while (resultSet.next()) {
                    val user = User()
                    this.auxLoad(user, resultSet)
                    list.add(user)
                }
            }
        } catch (ex: SQLException) {
            println(ex.message)
        }
        return list
    }

    fun getSingleUser(id: Int): User? {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val user = User()
        user.id = id
        val stmt = "select * from app_user where id=?"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                ps.setInt(1, id)
                val resultSet = ps.executeQuery()
                if (resultSet.next()) {
                    auxLoad(user, resultSet)
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return user
    }

    private fun auxLoad(user: User, resultSet: ResultSet) {
        try {
            user.id = resultSet.getInt("id")
            user.name = resultSet.getString("name")
            user.password = resultSet.getString("password")
            user.permissions01 = resultSet.getString("permissions01")
            user.permissions02 = resultSet.getString("permissions02")
            user.permissions03 = resultSet.getString("permissions03")
            user.permissions04 = resultSet.getString("permissions04")
            user.permissions05 = resultSet.getString("permissions05")
            user.permissions06 = resultSet.getString("permissions06")
            user.permissions07 = resultSet.getString("permissions07")
            user.permissions08 = resultSet.getString("permissions08")
            user.permissions09 = resultSet.getString("permissions09")
            user.permissions10 = resultSet.getString("permissions10")
            user.permissions11 = resultSet.getString("permissions11")
            user.permissions12 = resultSet.getString("permissions12")
            user.permissions13 = resultSet.getString("permissions13")
            user.permissions14 = resultSet.getString("permissions14")
            user.permissions15 = resultSet.getString("permissions15")
            user.permissions16 = resultSet.getString("permissions16")
            user.permissions17 = resultSet.getString("permissions17")
            user.permissions18 = resultSet.getString("permissions18")
            user.permissions19 = resultSet.getString("permissions19")
            user.permissions20 = resultSet.getString("permissions20")
            user.permissions21 = resultSet.getString("permissions21")
            user.permissions22 = resultSet.getString("permissions22")
            user.permissions23 = resultSet.getString("permissions23")
            user.permissions24 = resultSet.getString("permissions24")
            user.permissions25 = resultSet.getString("permissions25")
            user.permissions26 = resultSet.getString("permissions26")
            user.permissions27 = resultSet.getString("permissions27")
            user.permissions28 = resultSet.getString("permissions28")
            user.permissions29 = resultSet.getString("permissions29")
            user.permissions30 = resultSet.getString("permissions30")
            user.permissions31 = resultSet.getString("permissions31")
            user.permissions32 = resultSet.getString("permissions32")
            user.permissions33 = resultSet.getString("permissions33")
            user.permissions34 = resultSet.getString("permissions34")
            user.permissions35 = resultSet.getString("permissions35")
            user.permissions36 = resultSet.getString("permissions36")
            user.permissions37 = resultSet.getString("permissions37")
            user.permissions38 = resultSet.getString("permissions38")
            user.permissions39 = resultSet.getString("permissions39")
            user.permissions40 = resultSet.getString("permissions40")
            user.permissionsGeneral = resultSet.getString("permissions_general")
            user.isActive = CustomBoolean(resultSet.getInt("active")).boolean
        } catch (ex: SQLException) {
            println(ex.message)
        }
    }
}