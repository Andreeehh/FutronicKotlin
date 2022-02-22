package domain


class Service : Comparable<Service?> {
    var id = 0
    var name: String? = null
    var description: String? = null
    var internalCode: String? = null
    var barcode: String? = null
    var department: Department? = null
    var type = 0
    var brand: Brand? = null
    var category: Category? = null
        private set
    var measureUnit: String? = null
    var price = 0
    var auxPrice = 0
    var minimumStock = 0.0
    var currentStock = 0.0
    var amount = 0.0
    var isProductionPrint = false
    var serviceionPaperId = 0
    var pCO = 0
    var isWeb = false
    var isSelectionPanel = false
    var pcpGroup = 0
    var pcpColumn = 0
    var tax1 = 0.0
    var tax2 = 0.0
    var tax3 = 0.0
    var tax4 = 0.0
    var tax5 = 0.0
    var tax6 = 0.0
    var tax1Code: String? = null
    var tax2Code: String? = null
    var tax3Code: String? = null
    var tax4Code: String? = null
    var tax5Code: String? = null
    var tax6Code: String? = null
    var ncm: String? = null
    private var cFop: String? = null
    var isActive = false
    private var items: ArrayList<ServiceItem>? = null

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
        price = 0
        auxPrice = 0
        minimumStock = 0.0
        currentStock = 0.0
        amount = 0.0
        isProductionPrint = false
        serviceionPaperId = 0
        pCO = 0
        isWeb = false
        isSelectionPanel = false
        pcpGroup = 0
        pcpColumn = 0
        tax1 = 0.0
        tax2 = 0.0
        tax3 = 0.0
        tax4 = 0.0
        tax5 = 0.0
        tax6 = 0.0
        tax1Code = ""
        tax2Code = ""
        tax3Code = ""
        tax4Code = ""
        tax5Code = ""
        tax6Code = ""
        ncm = ""
        cFop = ""
        isActive = false
        items = ArrayList<ServiceItem>()
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
        price = 0
        auxPrice = 0
        minimumStock = 0.0
        currentStock = 0.0
        amount = 0.0
        isProductionPrint = false
        serviceionPaperId = 0
        pCO = 0
        isWeb = false
        isSelectionPanel = false
        pcpGroup = 0
        pcpColumn = 0
        tax1 = 0.0
        tax2 = 0.0
        tax3 = 0.0
        tax4 = 0.0
        tax5 = 0.0
        tax6 = 0.0
        tax1Code = ""
        tax2Code = ""
        tax3Code = ""
        tax4Code = ""
        tax5Code = ""
        tax6Code = ""
        ncm = ""
        cFop = ""
        this.isActive = isActive
        items = ArrayList<ServiceItem>()
    }

    constructor(service: Service) {
        copyFrom(service)
    }

    fun copyFrom(service: Service) {
        id = service.id
        name = service.name
        description = service.description
        internalCode = service.internalCode
        barcode = service.barcode
        department = service.department
        type = service.type
        brand = service.brand
        this.category = service.category
        measureUnit = service.measureUnit
        price = service.price
        auxPrice = service.auxPrice
        minimumStock = service.minimumStock
        currentStock = service.currentStock
        amount = service.amount
        isProductionPrint = service.isProductionPrint
        serviceionPaperId = service.serviceionPaperId
        pCO = service.pCO
        isWeb = service.isWeb
        isSelectionPanel = service.isSelectionPanel
        pcpGroup = service.pcpGroup
        pcpColumn = service.pcpColumn
        tax1 = service.tax1
        tax2 = service.tax2
        tax3 = service.tax3
        tax4 = service.tax4
        tax5 = service.tax5
        tax6 = service.tax6
        tax1Code = service.tax1Code
        tax2Code = service.tax2Code
        tax3Code = service.tax3Code
        tax4Code = service.tax4Code
        tax5Code = service.tax5Code
        tax6Code = service.tax6Code
        ncm = service.ncm
        cFop = service.getcFop()
        isActive = service.isActive
        items = service.getItems()
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
            } else "Produ\ufffd\ufffdo"
        }

    fun setType(typeString: String) {
        if (typeString.equals("Revenda", ignoreCase = true)) {
            type = 1
        } else {
            type = 2
        }
    }

    fun setProductCategory(category: Category?) {
        this.category = category
    }

    val printerIdString: String
        get() = Integer.toString(serviceionPaperId)

    fun setPrinterId(printerId: String) {
        serviceionPaperId = printerId.toInt()
    }

    fun getcFop(): String? {
        return cFop
    }

    fun setcFop(cFop: String?) {
        this.cFop = cFop
    }

    fun getItems(): ArrayList<ServiceItem>? {
        return items
    }

    fun setItems(items: ArrayList<ServiceItem>?) {
        this.items = items
    }

    fun getItemById(id: Int): ServiceItem? {
        for (item in items!!) {
            if (item.id === id) {
                return item
            }
        }
        return null
    }

    fun containsProduct(product: Product): Boolean {
        for (item in items!!) {
            if (item.itemProduct!!.id === product.id) {
                return true
            }
        }
        return false
    }

    fun getProductAmount(product: Product): Int {
        var ret = 0
        for (item in items!!) {
            if (item.itemProduct!!.id === product.id) {
                ret += item.amountTime as Int
            }
        }
        return ret
    }

    fun containsRawMaterial(material: Material): Boolean {
        for (item in items!!) {
            if (item.material!!.id === material.id) {
                return true
            }
        }
        return false
    }

    fun getRawMaterialAmount(material: Material): Int {
        var ret = 0
        for (item in items!!) {
            if (item.material!!.id === material.id) {
                ret += item.amountTime as Int
            }
        }
        return ret
    }

    fun containsManufacture(manufacture: Manufacture): Boolean {
        for (item in items!!) {
            if (item.manufacture!!.id === manufacture.id) {
                return true
            }
        }
        return false
    }

    fun getManufactureAmount(manufacture: Manufacture): Int {
        var ret = 0
        for (item in items!!) {
            if (item.manufacture!!.id === manufacture.id) {
                ret += item.amountTime as Int
            }
        }
        return ret
    }

    fun getManufactureAloction(manufacture: Manufacture): Int {
        var ret = 0
        for (item in items!!) {
            if (item.manufacture!!.id === manufacture.id) {
                ret += item.wasteAlocation as Int
            }
        }
        return ret
    }

    fun getManufacturePosition(manufacture: Manufacture): Int {
        var ret = 1
        for (i in items!!.indices.reversed()) {
            val item: ServiceItem = items!![i]
            if (item.type === 3) {
                if (item.manufacture!!.id === manufacture.id) {
                    return ret
                }
                ++ret
            }
        }
        return 0
    }

    fun getManufactureByPosition(position: Int): Manufacture {
        var index = 1
        for (i in items!!.indices.reversed()) {
            val item: ServiceItem = items!![i]
            if (item.type === 3) {
                if (index == position) {
                    return item.manufacture!!
                }
                ++index
            }
        }
        return Manufacture()
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
        val other = obj as Service
        return id == other.id
    }

    override fun compareTo(service2: Service?): Int {
        return id - service2!!.id
    }
}
