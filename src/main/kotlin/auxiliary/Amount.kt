package auxiliary

import java.text.DecimalFormat


class Amount : Comparable<Amount?> {
    val amount: Double

    constructor(amount: Double) {
        this.amount = amount
    }

    constructor(amount: Int) {
        this.amount = amount.toDouble()
    }

    constructor(amountString: String) {
        var text = amountString.replace("[^0-9,-]".toRegex(), "")
        if (text == "") {
            text = "0"
        }
        text = text.replace(",".toRegex(), ".")
        amount = text.toDouble()
    }

    val x1000IntValue: Int
        get() = (amount * 1000.0).toInt()
    val divBy1000Amount: Double
        get() = amount / 1000.0

    fun toString4Decimals(): String {
        val df = DecimalFormat("0.0000")
        return df.format(amount).replace(",", ".")
    }

    fun toString3Decimals(separator: String?): String {
        val df = DecimalFormat("0.000")
        return df.format(amount).replace(",", separator!!)
    }

    fun toStringNoDecimal(): String {
        return divBy1000Amount.toString().replace(".0".toRegex(), "")
    }

    override fun toString(): String {
        val df: DecimalFormat
        df = if (!amount.toString().contains(".")) {
            DecimalFormat("#,###,###,###")
        } else {
            DecimalFormat("#,###,###,##0.###")
        }
        return df.format(amount)
    }

    override fun compareTo(amount2: Amount?): Int {
        return x1000IntValue - amount2!!.x1000IntValue
    }
}
