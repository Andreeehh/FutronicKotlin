package domain


class Card : Comparable<Card?> {
    var id = 0
    var isInUse = false
    var saleId = 0
    var isProductionPrint = false
    var isDelivered = false
    var openingTime: String? = null
    var lastItemTime: String? = null
    private var hasItems = false
    var hallTableId = 0

    constructor() {
        id = 0
        isInUse = false
        saleId = 0
        isProductionPrint = false
        isDelivered = false
        openingTime = "00:00"
        lastItemTime = "00:00"
        hasItems = false
        hallTableId = 0
    }

    constructor(id: Int) {
        this.id = id
        isInUse = false
        saleId = 0
        isProductionPrint = false
        isDelivered = false
        openingTime = "00:00"
        lastItemTime = "00:00"
        hasItems = false
        hallTableId = 0
    }

    constructor(card: Card) {
        copyFrom(card)
    }

    fun copyFrom(card: Card) {
        id = card.id
        isInUse = card.isInUse
        saleId = card.saleId
        isProductionPrint = card.isProductionPrint
        isDelivered = card.isDelivered
        openingTime = card.openingTime
        lastItemTime = card.lastItemTime
        hasItems = card.hasItems()
        hallTableId = card.hallTableId
    }

    val name: String
        get() = Integer.toString(id)

    fun hasItems(): Boolean {
        return hasItems
    }

    fun setHasItems(hasItems: Boolean) {
        this.hasItems = hasItems
    }

    val hallTableName: String
        get() = if (hallTableId < 10) {
            "0" + hallTableId
        } else hallTableId.toString()

    override fun toString(): String {
        return name
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
        val other = obj as Card
        return id == other.id
    }

    override fun compareTo(card2: Card?): Int {
        return id - card2!!.id
    }
}
