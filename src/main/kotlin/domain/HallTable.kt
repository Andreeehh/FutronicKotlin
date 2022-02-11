package domain


class HallTable : Comparable<HallTable?> {
    var id = 0
    var isInUse = false
    var saleId = 0
    var isProductionPrint = false
    var isDelivered = false
    var openingTime: String? = null
    var lastItemTime: String? = null
    private var hasItems = false

    constructor() {
        id = 0
        isInUse = false
        saleId = 0
        isProductionPrint = false
        isDelivered = false
        openingTime = "00:00"
        lastItemTime = "00:00"
        hasItems = false
    }

    constructor(id: Int, isActive: Boolean) {
        this.id = id
        isInUse = false
        saleId = 0
        isProductionPrint = false
        isDelivered = false
        openingTime = "00:00"
        lastItemTime = "00:00"
        hasItems = false
    }

    constructor(hallTable: HallTable) {
        copyFrom(hallTable)
    }

    fun copyFrom(hallTable: HallTable) {
        id = hallTable.id
        isInUse = hallTable.isInUse
        saleId = hallTable.saleId
        isProductionPrint = hallTable.isProductionPrint
        isDelivered = hallTable.isDelivered
        openingTime = hallTable.openingTime
        lastItemTime = hallTable.lastItemTime
        hasItems = hallTable.hasItems()
    }

    val name: String
        get() = Integer.toString(id)

    fun hasItems(): Boolean {
        return hasItems
    }

    fun setHasItems(hasItems: Boolean) {
        this.hasItems = hasItems
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
        val other = obj as HallTable
        return id == other.id
    }

    override fun compareTo(hallTable2: HallTable?): Int {
        return id - hallTable2!!.id
    }
}
