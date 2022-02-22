package security

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and


class Crypt {
    var isValid = false
        private set

    constructor() {}
    constructor(rawText: String, encryptedText: String) {
        isValid = false
        try {
            val md = MessageDigest.getInstance("MD5")
            isValid = byteArrayToHexString(md.digest(rawText.toByteArray())) == encryptedText
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }


    fun getNumber(code: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            for (i in 0..1999) {
                val toAply = Integer.toString(i)
                if (byteArrayToHexString(md.digest(toAply.toByteArray())) == code) {
                    return toAply
                }
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return "0"
    }

    fun getCrypted(text: String): String {
        var ret = ""
        try {
            val md = MessageDigest.getInstance("MD5")
            ret = byteArrayToHexString(md.digest(text.toByteArray()))!!
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ret
    }

    companion object {
        private fun byteArrayToHexString(b: ByteArray): String? {
            val result = StringBuilder()
            for (value in b) {
                result.append(((value.toInt() and 0xFF) + 256).toString(16).substring(1))
            }
            return result.toString()
        }
    }
}
