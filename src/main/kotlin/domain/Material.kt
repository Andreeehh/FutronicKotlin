package domain


class Material : Comparable<Material?> {
    var id = 0
    var name: String? = null
    var description: String? = null
    var internalCode: String? = null
    var barcode: String? = null
    var supplier: Supplier? = null
    var brand: Brand? = null
    var category: Category? = null
    var purchaseAmount = 0.0
    var isWarehouse = false
    var measureUnit: String? = null
    var price = 0
    var auxPrice = 0
    var cost = 0
    var loss = 0.0
    var minimumStock = 0.0
    var currentStock = 0.0
    var tax1 = 0.0
    var tax2 = 0.0
    var tax3 = 0.0
    var tax4 = 0.0
    var tax5 = 0.0
    var tax6 = 0.0
    var isActive = false
    var type = 0
    private var items: ArrayList<MaterialItem>? = null
    var produce = 0.0

    constructor() {
        id = 0
        name = ""
        description = ""
        internalCode = ""
        barcode = ""
        supplier = Supplier()
        brand = Brand()
        this.category = Category()
        purchaseAmount = 0.0
        isWarehouse = false
        measureUnit = "u"
        price = 0
        auxPrice = 0
        cost = 0
        loss = 0.0
        minimumStock = 0.0
        currentStock = 0.0
        tax1 = 0.0
        tax2 = 0.0
        tax3 = 0.0
        tax4 = 0.0
        tax5 = 0.0
        tax6 = 0.0
        isActive = false
        type = 0
        produce = 0.0
        items = ArrayList<MaterialItem>()
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        description = ""
        internalCode = ""
        barcode = ""
        supplier = Supplier()
        brand = Brand()
        this.category = Category()
        purchaseAmount = 0.0
        isWarehouse = false
        measureUnit = "u"
        price = 0
        auxPrice = 0
        cost = 0
        loss = 0.0
        minimumStock = 0.0
        currentStock = 0.0
        tax1 = 0.0
        tax2 = 0.0
        tax3 = 0.0
        tax4 = 0.0
        tax5 = 0.0
        tax6 = 0.0
        this.isActive = isActive
        type = 0
        produce = 0.0
        items = ArrayList<MaterialItem>()
    }

    constructor(material: Material) {
        copyFrom(material)
    }

    fun copyFrom(material: Material) {
        id = material.id
        name = material.name
        description = material.description
        internalCode = material.internalCode
        barcode = material.barcode
        supplier = material.supplier
        brand = material.brand
        this.category = material.category
        purchaseAmount = material.purchaseAmount
        isWarehouse = material.isWarehouse
        measureUnit = material.measureUnit
        price = material.price
        auxPrice = material.auxPrice
        cost = material.cost
        loss = material.loss
        minimumStock = material.minimumStock
        currentStock = material.currentStock
        tax1 = material.tax1
        tax2 = material.tax2
        tax3 = material.tax3
        tax4 = material.tax4
        tax5 = material.tax5
        tax6 = material.tax6
        isActive = material.isActive
        type = material.type
        produce = material.produce
        items = material.getItems()
    }

    fun getShortName(numChars: Int): String? {
        return if (name!!.length < numChars) {
            name
        } else name!!.substring(0, numChars)
    }

    fun getItems(): ArrayList<MaterialItem>? {
        return items
    }

    fun setItems(items: ArrayList<MaterialItem>?) {
        this.items = items
    }

    fun getItemById(id: Int): MaterialItem? {
        for (item in items!!) {
            if (item.id === id) {
                return item
            }
        }
        return null
    }

    fun getItemByDepartment(department: Department?): MaterialItem? {
        for (item in items!!) {
            if (item.department!! == department) {
                return item
            }
        }
        return null
    }

    fun containsDepartment(department: Department?): Boolean {
        for (item in items!!) {
            if (item.department!! == department) {
                return true
            }
        }
        return false
    }

    fun getDepartmentPercent(department: Department?): Double {
        for (item in items!!) {
            if (item.department!! == department) {
                return item.percent
            }
        }
        return 0.0
    }

    fun getDepartmentFraction(department: Department?): Double {
        for (item in items!!) {
            if (item.department!! == department) {
                return item.percent / 100.0
            }
        }
        return 0.0
    }

    val productPercent: Double
        get() {
            var acc = 0.0
            for (item in items!!) {
                acc += item.percent
            }
            return 100.0 - acc
        }
    val productFraction: Double
        get() {
            var acc = 0.0
            for (item in items!!) {
                acc += item.percent
            }
            return (100.0 - acc) / 100.0
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
        val other = obj as Material
        return id == other.id
    }

    override fun compareTo(rawMaterial2: Material?): Int {
        return id - rawMaterial2!!.id
    }
}
