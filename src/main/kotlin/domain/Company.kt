package domain

import java.text.DecimalFormat


class Company : Comparable<Company?> {
    var id = 0
    var name: String? = null
    var value = 0
    var expirationDay = 0
    var expirationMonthOffset = 0
    var companyCreditLimit = 0
    var clientCreditLimit = 0
    var isActive = false

    constructor() {
        id = 0
        name = ""
        value = 0
        expirationDay = 0
        expirationMonthOffset = 0
        companyCreditLimit = 0
        clientCreditLimit = 0
        isActive = false
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        value = 0
        expirationDay = 0
        expirationMonthOffset = 0
        companyCreditLimit = 0
        clientCreditLimit = 0
        this.isActive = isActive
    }

    constructor(company: Company) {
        copyFrom(company)
    }

    fun copyFrom(company: Company) {
        id = company.id
        name = company.name
        value = company.value
        expirationDay = company.expirationDay
        expirationMonthOffset = company.expirationMonthOffset
        companyCreditLimit = company.companyCreditLimit
        clientCreditLimit = company.clientCreditLimit
        isActive = company.isActive
    }

    val valueString: String
        get() {
            val df = DecimalFormat("#,###,###,##0.00")
            return df.format(value / 100.0)
        }

    fun setValue(valueString: String) {
        value = valueString.replace("[^0-9]".toRegex(), "").toDouble().toInt()
    }

    override fun toString(): String {
        return id.toString()
    }


    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = 31 * result + id
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) {
            return true
        }
        if (obj == null) {
            return false
        }
        if (this.javaClass != obj.javaClass) {
            return false
        }
        val other = obj as Company
        return id == other.id
    }

    override fun compareTo(company2: Company?): Int {
        return id - company2!!.id
    }
}
