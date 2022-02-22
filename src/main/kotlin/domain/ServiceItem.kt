package domain


class ServiceItem {
    var id = 0
    var service: Service? = null
    var type = 0
    var itemProduct: Product? = null
    var material: Material? = null
    var manufacture: Manufacture? = null
    var amountTime = 0.0
    var unit: String? = null
    var wasteAlocation = 0.0
    var notes: String? = null
    var isInclude = false

    constructor() {
        id = 0
        service = Service()
        type = 0
        itemProduct = Product()
        material = Material()
        manufacture = Manufacture()
        amountTime = 0.0
        unit = ""
        wasteAlocation = 0.0
        notes = ""
        isInclude = false
    }

    constructor(serviceItem: ServiceItem) {
        copyFrom(serviceItem)
    }

    fun copyFrom(serviceItem: ServiceItem) {
        id = serviceItem.id
        service = serviceItem.service
        type = serviceItem.type
        itemProduct = serviceItem.itemProduct
        material = serviceItem.material
        manufacture = serviceItem.manufacture
        amountTime = serviceItem.amountTime
        unit = serviceItem.unit
        wasteAlocation = serviceItem.wasteAlocation
        notes = serviceItem.notes
        isInclude = serviceItem.isInclude
    }

    val typeString: String
        get() {
            if (type == 1) {
                return "Produto"
            }
            return if (type == 2) {
                "Materiais"
            } else "Manufatura"
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
        } else {
            type = 3
        }
    }

    val name: String?
        get() {
            if (type == 1) {
                return itemProduct!!.name
            }
            if (type == 2) {
                return material!!.name
            }
            return if (type == 3) {
                manufacture!!.name
            } else ""
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
        val other = obj as ServiceItem
        return id == other.id
    }
}
