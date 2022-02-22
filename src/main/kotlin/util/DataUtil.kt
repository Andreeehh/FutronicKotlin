package util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import kotlin.experimental.and


class DataUtil {
    val banco = "jdbc:mysql://localhost:3306/db000"

    var con: Connection? = null

    private fun byteArrayToHexString(b: ByteArray): String? {
        val result = StringBuilder()
        for (value in b) {
            result.append(((value.toInt() and 0xFF) + 256).toString(16).substring(1))
        }
        return result.toString()
    }

    fun getDBPassword(passwordString: String): String? {
        var md: MessageDigest? = null
        try {
            md = MessageDigest.getInstance("MD5")
        } catch (ex: NoSuchAlgorithmException) {
            ex.printStackTrace()
        }
        if (md == null) {
            return null;
        }

        return byteArrayToHexString(md.digest(passwordString.toByteArray()));
    }

    fun getUserToLogin(pass: String): Int {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val stmt = "select id from app_user where password = ?;"
        val ps: PreparedStatement
        try {
            ps = con!!.prepareStatement(stmt)
            ps.setString(1, pass)
            val resultSet = ps.executeQuery()
            if (resultSet.next()) {
                return resultSet.getInt("id")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return 0
    }
}