package domain


class ProductItem {
    var id = 0
    var product: Product? = null
    var type = 0
    var itemProduct: Product? = null
    private var material: Material? = null
    private var manufacture: Manufacture? = null
    private var equipment: Equipment? = null
    var amountTime = 0.0
    var unit: String? = null
    var wasteAlocation = 0.0
    var notes: String? = null
    var isInclude = false

    constructor() {
        id = 0
        product = Product()
        type = 0
        itemProduct = Product()
        material = Material()
        manufacture = Manufacture()
        equipment = Equipment()
        amountTime = 0.0
        unit = ""
        wasteAlocation = 0.0
        notes = ""
        isInclude = false
    }

    constructor(productItem: ProductItem) {
        copyFrom(productItem)
    }

    fun copyFrom(productItem: ProductItem) {
        id = productItem.id
        product = productItem.product
        type = productItem.type
        itemProduct = productItem.itemProduct
        material = productItem.material
        manufacture = productItem.manufacture
        equipment = productItem.equipment
        amountTime = productItem.amountTime
        unit = productItem.unit
        wasteAlocation = productItem.wasteAlocation
        notes = productItem.notes
        isInclude = productItem.isInclude
    }

    val typeString: String
        get() {
            if (type == 1) {
                return "Produto"
            }
            if (type == 2) {
                return "Materiais"
            }
            if (type == 3) {
                return "Manufatura"
            }
            return if (type == 4) {
                "Equipamento"
            } else ""
        }

    fun setType(typeString: String) {
        if (typeString.equals("Produto", ignoreCase = true)) {
            type = 1
        } else if (typeString.equals("Materiais", ignoreCase = true) || typeString.equals(
                "Matéria-prima",
                ignoreCase = true
            )
        ) {
            type = 2
        } else if (typeString.equals("Manufatura", ignoreCase = true)) {
            type = 3
        } else if (typeString.equals("Equipamento", ignoreCase = true)) {
            type = 4
        } else {
            type = 9
        }
    }

    fun getMaterial(): Material? {
        return material
    }

    fun setMaterial(material: Material?) {
        this.material = material
    }

    fun getManufacture(): Manufacture? {
        return manufacture
    }

    fun setManufacture(manufacture: Manufacture?) {
        this.manufacture = manufacture
    }

    fun getEquipment(): Equipment? {
        return equipment
    }

    fun setEquipment(equipment: Equipment?) {
        this.equipment = equipment
    }

    val name: String?
        get() {
            if (type == 1) {
                return itemProduct!!.name
            }
            if (type == 2) {
                return getMaterial()!!.name
            }
            if (type == 3) {
                return getManufacture()!!.name
            }
            return if (type == 4) {
                getEquipment()!!.name
            } else ""
        }
    val value: Int
        get() {
            if (type == 1) {
                return if (itemProduct!!.comboPrice > 0) {
                    itemProduct!!.comboPrice
                } else itemProduct!!.cost
            }
            return if (type == 2) {
                getMaterial()!!.price
            } else 0
        }

    override fun toString(): String {
        return typeString
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
        val other = obj as ProductItem
        return id == other.id
    }
}
