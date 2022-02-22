package auxiliary

import java.text.DecimalFormat


class Currency : Comparable<Currency?> {
    val intValue: Int

    constructor() {
        intValue = 0
    }

    constructor(value: Int) {
        intValue = value
    }

    constructor(value: Double) {
        intValue = value.toInt()
    }

    constructor(value: String) {
        var text = value.replace("[^0-9]".toRegex(), "")
        if (text.equals("", ignoreCase = true)) {
            text = "0"
        }
        intValue = text.toInt()
    }

    fun getIntValueMultiplyDoubleBy100(value: Double): Int {
        return (value * 1000.0 / 10.0).toInt()
    }

    fun toDouble(): Double {
        return intValue / 100.0
    }

    fun toDobuleString2Decimals(): String {
        val df = DecimalFormat("0.00")
        return df.format(intValue / 100.0).replace(",", ".")
    }

    override fun toString(): String {
        val df = DecimalFormat("#,###,###,##0.00")
        return df.format(intValue / 100.0)
    }

    override fun compareTo(value2: Currency?): Int {
        return intValue - value2!!.intValue
    }
}
