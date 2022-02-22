package domain


class EquipmentItem {
    var id = 0
    var equipment: Equipment? = null
    var type = 0
    var itemEquipment: Equipment? = null
    private var product: Product? = null
    var productId = 0
    var amount = 0.0
    var value = 0
    var updateDate: String? = null
    var updateTime: String? = null
    var expirationDate: String? = null
    var expirationTime: String? = null
    var notes: String? = null
    var isInclude = false

    constructor() {
        id = 0
        type = 0
        equipment = Equipment()
        itemEquipment = Equipment()
        product = Product()
        amount = 0.0
        value = 0
        updateDate = ""
        updateTime = ""
        expirationDate = ""
        expirationTime = ""
        notes = ""
        isInclude = false
    }

    constructor(equipmentItem: EquipmentItem) {
        copyFrom(equipmentItem)
    }

    fun copyFrom(equipmentItem: EquipmentItem) {
        id = equipmentItem.id
        type = equipmentItem.type
        equipment = equipmentItem.equipment
        itemEquipment = equipmentItem.itemEquipment
        product = equipmentItem.product
        amount = equipmentItem.amount
        value = equipmentItem.value
        updateDate = equipmentItem.updateDate
        updateTime = equipmentItem.updateTime
        expirationDate = equipmentItem.expirationDate
        expirationTime = equipmentItem.expirationTime
        notes = equipmentItem.notes
        isInclude = equipmentItem.isInclude
    }

    fun getProduct(): Product? {
        return product
    }

    fun setProduct(product: Product?) {
        this.product = product
        if (product != null) {
            productId = product.id
        }
    }

    val name: String?
        get() {
            if (type == 1) {
                return itemEquipment!!.name
            }
            return if (type == 2) {
                getProduct()!!.name
            } else ""
        }

    override fun toString(): String {
        if (type == 1) {
            return itemEquipment!!.name!!
        }
        return if (type == 2) {
            product!!.name!!
        } else notes!!
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
        val other = obj as EquipmentItem
        return id == other.id
    }
}
