package domain


class Equipment : Comparable<Equipment?> {
    var id = 0
    var name: String? = null
    var description: String? = null
    var internalCode: String? = null
    var barcode: String? = null
    var department: Department? = null
    var type = 0
    var brand: Brand? = null
    var category: Category? = null
    var measureUnit: String? = null
    var minimumStock = 0.0
    var currentStock = 0.0
    var isProductionPrint = false
    var productionPaperId = 0
        private set
    var state = 0
    var isActive = false
    private var items: ArrayList<EquipmentItem>? = null

    constructor() {
        id = 0
        name = ""
        description = ""
        internalCode = ""
        barcode = ""
        department = Department()
        type = 1
        brand = Brand()
        this.category = Category()
        measureUnit = "u"
        minimumStock = 0.0
        currentStock = 0.0
        isProductionPrint = false
        productionPaperId = 0
        state = 0
        isActive = false
        items = ArrayList<EquipmentItem>()
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        description = ""
        internalCode = ""
        barcode = ""
        department = Department()
        type = 1
        brand = Brand()
        this.category = Category()
        measureUnit = "u"
        minimumStock = 0.0
        currentStock = 0.0
        isProductionPrint = false
        productionPaperId = 0
        state = 0
        this.isActive = isActive
        items = ArrayList<EquipmentItem>()
    }

    constructor(equipment: Equipment) {
        copyFrom(equipment)
    }

    fun copyFrom(equipment: Equipment) {
        id = equipment.id
        name = equipment.name
        description = equipment.description
        internalCode = equipment.internalCode
        barcode = equipment.barcode
        department = equipment.department
        type = equipment.type
        brand = equipment.brand
        this.category = equipment.category
        measureUnit = equipment.measureUnit
        minimumStock = equipment.minimumStock
        currentStock = equipment.currentStock
        isProductionPrint = equipment.isProductionPrint
        productionPaperId = equipment.productionPaperId
        state = equipment.state
        isActive = equipment.isActive
        items = equipment.getItems()
    }

    fun getShortName(numChars: Int): String? {
        return if (name!!.length < numChars) {
            name
        } else name!!.substring(0, numChars)
    }

    val nameNoCategory: String?
        get() = if (name!!.trim { it <= ' ' }.toLowerCase()
                .startsWith(this.category!!.name!!.trim { it <= ' ' }.toLowerCase())
        ) {
            name!!.replaceFirst(this.category!!.name + " ".toRegex(), "")
        } else name
    val typeString: String
        get() {
            return if (type == 1) {
                "Revenda"
            } else "Produção"
        }

    fun setType(typeString: String) {
        if (typeString.equals("Revenda", ignoreCase = true)) {
            type = 1
        } else {
            type = 2
        }
    }

    val printerIdString: String
        get() = Integer.toString(productionPaperId)

    fun setPrinterId(printerId: Int) {
        productionPaperId = printerId
    }

    fun setPrinterId(printerId: String) {
        productionPaperId = printerId.toInt()
    }

    val stateString: String
        get() {
            if (state == 1) {
                return "Disponível"
            }
            if (state == 2) {
                return "Cliente"
            }
            if (state == 3) {
                return "Vazio"
            }
            if (state == 4) {
                return "Fábrica"
            }
            if (state == 5) {
                return "Troca"
            }
            if (state == 6) {
                return "Extra"
            }
            return if (state == 7) {
                "Sinistro"
            } else "Indefinido"
        }

    fun getItems(): ArrayList<EquipmentItem>? {
        return items
    }

    fun setItems(items: ArrayList<EquipmentItem>?) {
        this.items = items
    }

    fun getItemById(id: Int): EquipmentItem? {
        for (item in items!!) {
            if (item.id === id) {
                return item
            }
        }
        return null
    }

    fun containsEquipment(equipment: Equipment): Boolean {
        for (item in items!!) {
            if (item.itemEquipment!!.id === equipment.id) {
                return true
            }
        }
        return false
    }

    fun containsAnyProduct(): Boolean {
        for (item in items!!) {
            if (item.type === 2) {
                return true
            }
        }
        return false
    }

    val type2Item: EquipmentItem?
        get() {
            for (item in items!!) {
                if (item.type === 2) {
                    return item
                }
            }
            return null
        }

    fun getEquipmentAmount(equipment: Equipment): Int {
        var ret = 0
        for (item in items!!) {
            if (item.itemEquipment!!.id === equipment.id) {
                ret += item.amount as Int
            }
        }
        return ret
    }

    val sum: Int
        get() {
            var ret = 0
            for (item in items!!) {
                if (item.type === 3) {
                    ret += item.amount as Int
                }
            }
            return ret
        }

    override fun toString(): String {
        return internalCode!!
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
        val other = obj as Equipment
        return id == other.id
    }

    override fun compareTo(equipment2: Equipment?): Int {
        return id - equipment2!!.id
    }
}
