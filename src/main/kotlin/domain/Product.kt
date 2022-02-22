package domain

import java.util.*

class Product : Comparable<Product?> {
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
    var price = 0
    var auxPrice = 0
    var minimumPrice = 0
    var maximumPrice = 0
    var webSalePrice = 0
    var cost = 0
    var markup = 0.0
    var minimumStock = 0.0
    var currentStock = 0.0
    var amount = 0.0
    var isPrintProduction = false
    var printerId = 0
    var printerId2 = 0
        set
    var pAOCR = 0
    var webAuto = 0
    var panel = 0
    var supplier: Supplier? = null
    var purchaseAmount = 0.0
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
    var parameters: String? = null
    var isActive = false
    var comboPrice = 0
    var items: ArrayList<ProductItem>? = null
    var validity: String? = null
    var productionGroup = 0
    var productionGroup2 = 0

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
        minimumPrice = 0
        maximumPrice = 0
        webSalePrice = 0
        cost = 0
        markup = 0.0
        minimumStock = 0.0
        currentStock = 0.0
        amount = 0.0
        isPrintProduction = false
        printerId = 1
        printerId2 = 0
        pAOCR = 0
        webAuto = 0
        panel = 0
        supplier = Supplier()
        purchaseAmount = 0.0
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
        parameters = ""
        isActive = false
        items = ArrayList()
        comboPrice = 0
        validity = ""
        productionGroup = 1
        productionGroup2 = 0
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
        minimumPrice = 0
        maximumPrice = 0
        webSalePrice = 0
        cost = 0
        markup = 0.0
        minimumStock = 0.0
        currentStock = 0.0
        amount = 0.0
        isPrintProduction = false
        printerId = 1
        printerId2 = 2
        pAOCR = 0
        webAuto = 0
        panel = 0
        supplier = Supplier()
        purchaseAmount = 0.0
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
        parameters = ""
        this.isActive = isActive
        items = ArrayList()
        comboPrice = 0
        validity = ""
        productionGroup = 1
        productionGroup2 = 2
    }

    constructor(product: Product) {
        copyFrom(product)
    }

    fun copyFrom(product: Product) {
        id = product.id
        name = product.name
        description = product.description
        internalCode = product.internalCode
        barcode = product.barcode
        department = product.department
        type = product.type
        brand = product.brand
        this.category = product.category
        measureUnit = product.measureUnit
        price = product.price
        auxPrice = product.auxPrice
        minimumPrice = product.minimumPrice
        maximumPrice = product.maximumPrice
        webSalePrice = product.webSalePrice
        cost = product.cost
        markup = product.markup
        minimumStock = product.minimumStock
        currentStock = product.currentStock
        amount = product.amount
        isPrintProduction = product.isPrintProduction
        printerId = product.printerId
        printerId2 = product.printerId2
        pAOCR = product.pAOCR
        webAuto = product.webAuto
        panel = product.panel
        supplier = product.supplier
        purchaseAmount = product.purchaseAmount
        tax1 = product.tax1
        tax2 = product.tax2
        tax3 = product.tax3
        tax4 = product.tax4
        tax5 = product.tax5
        tax6 = product.tax6
        tax1Code = product.tax1Code
        tax2Code = product.tax2Code
        tax3Code = product.tax3Code
        tax4Code = product.tax4Code
        tax5Code = product.tax5Code
        tax6Code = product.tax6Code
        ncm = product.ncm
        cFop = product.getcFop()
        parameters = product.parameters
        isActive = product.isActive
        items = product.items
        comboPrice = product.comboPrice
        validity = product.validity
        productionGroup = product.productionGroup
        productionGroup2 = product.productionGroup2
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
    val nameNoShort: String
        get() = name!!.replace("Ad ", "").replace("Ad. ", "").replace("AD ", "").replace("AD. ", "").replace("ad ", "")
            .replace("ad. ", "").replace("c/ ", "").replace("C/ ", "")
    val lastName: String
        get() = name!!.split(" ").toTypedArray().get(name!!.split(" ").toTypedArray().size - 1)
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

    fun isPermittedForCategory(prodcat: Category): Boolean {
        val text = description!!.replace("[^0-9,]".toRegex(), "")
        return text.trim { it <= ' ' } == "" || ArrayList<Any?>(
            Arrays.asList(
                *text.split(",").toTypedArray()
            )
        ).contains(
            Integer.toString(prodcat.id)
        )
    }

    fun calculateMarkup(): Double {
        return 100.0 * (price - cost.toDouble()) / cost
    }

    val isAuto: Boolean
        get() = webAuto == 1 || webAuto == 3
    val isWeb: Boolean
        get() = webAuto == 1 || webAuto == 2

    fun getcFop(): String? {
        return cFop
    }

    fun setcFop(cFop: String?) {
        this.cFop = cFop
    }

    fun isCatMax(categoryId: Int): Boolean {
        val fields = parameters!!.split(",").toTypedArray()
        var array: Array<String>
        val length = fields.also { array = it }.size
        var i = 0
        while (i < length) {
            val field = array[i]
            if (field.contains("maxcat") && field.split("=").toTypedArray()[0] == "maxcat$categoryId") {
                return true
            }
            ++i
        }
        return false
    }

    fun getCatMax(categoryId: Int): Int {
        val fields = parameters!!.split(",").toTypedArray()
        var array: Array<String>
        val length = fields.also { array = it }.size
        var i = 0
        while (i < length) {
            val field = array[i]
            if (field.contains("maxcat") && field.split("=").toTypedArray()[0] == "maxcat$categoryId") {
                return field.split("=").toTypedArray()[1].toInt()
            }
            ++i
        }
        return 999
    }

    val isShowStock: Boolean
        get() {
            val fields = parameters!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("estoque")) {
                    return true
                }
                ++i
            }
            return false
        }
    val showStockMin: Int
        get() {
            val fields = parameters!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("estoque") && field.split("=").toTypedArray()[0] == "estoque") {
                    return field.split("=").toTypedArray()[1].toInt()
                }
                ++i
            }
            return 0
        }
    val isNoAmount: Boolean
        get() {
            val fields = parameters!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("noamount")) {
                    return true
                }
                ++i
            }
            return false
        }
    val isAutoPres: Boolean
        get() {
            val fields = parameters!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("autopres")) {
                    return true
                }
                ++i
            }
            return false
        }
    val isAutoExtra: Boolean
        get() {
            val fields = parameters!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("autoextra")) {
                    return true
                }
                ++i
            }
            return false
        }

    fun isCatCounter(categoryId: Int): Boolean {
        val fields = parameters!!.split(",").toTypedArray()
        var array: Array<String>
        val length = fields.also { array = it }.size
        var i = 0
        while (i < length) {
            val field = array[i]
            if (field.contains("countercat") && field.split("=").toTypedArray()[0] == "countercat$categoryId") {
                return true
            }
            ++i
        }
        return false
    }

    fun getCatCounterMultiplier(categoryId: Int): Int {
        val fields = parameters!!.split(",").toTypedArray()
        var array: Array<String>
        val length = fields.also { array = it }.size
        var i = 0
        while (i < length) {
            val field = array[i]
            if (field.contains("countercat") && field.split("=").toTypedArray()[0] == "countercat$categoryId") {
                return field.split("=").toTypedArray()[1].toInt()
            }
            ++i
        }
        return 999
    }

    fun getItemById(id: Int): ProductItem? {
        for (item in items!!) {
            if (item.id == id) {
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
                ret += item.amountTime.toInt()
            }
        }
        return ret
    }

    fun containsRawMaterial(material: Material): Boolean {
        for (item in items!!) {
            if (item.getMaterial()!!.id == material.id) {
                return true
            }
        }
        return false
    }

    fun getRawMaterialAmount(material: Material): Int {
        var ret = 0
        for (item in items!!) {
            if (item.getMaterial()!!.id == material.id) {
                ret += item.amountTime.toInt()
            }
        }
        return ret
    }

    fun containsManufacture(manufacture: Manufacture): Boolean {
        for (item in items!!) {
            if (item.getManufacture()!!.id == manufacture.id) {
                return true
            }
        }
        return false
    }

    fun getManufactureAmount(manufacture: Manufacture): Int {
        var ret = 0
        for (item in items!!) {
            if (item.getManufacture()!!.id == manufacture.id) {
                ret += item.amountTime.toInt()
            }
        }
        return ret
    }

    fun getManufactureAloction(manufacture: Manufacture): Int {
        var ret = 0
        for (item in items!!) {
            if (item.getManufacture()!!.id == manufacture.id) {
                ret += item.wasteAlocation.toInt()
            }
        }
        return ret
    }

    fun getManufacturePosition(manufacture: Manufacture): Int {
        var ret = 1
        for (i in items!!.indices.reversed()) {
            val item = items!![i]
            if (item.type == 3) {
                if (item.getManufacture()!!.id == manufacture.id) {
                    return ret
                }
                ++ret
            }
        }
        return 0
    }

    fun getManufactureByPosition(position: Int): Manufacture? {
        var index = 1
        for (i in items!!.indices.reversed()) {
            val item = items!![i]
            if (item.type == 3) {
                if (index == position) {
                    return item.getManufacture()
                }
                ++index
            }
        }
        return Manufacture()
    }

    fun containsEquipment(): Boolean {
        for (item in items!!) {
            if (item.type == 4) {
                return true
            }
        }
        return false
    }

    val isNotesWindow: Boolean
        get() {
            val fields = parameters!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.toLowerCase() == "obsjanela") {
                    return true
                }
                ++i
            }
            return false
        }
    val isNotesPanel: Boolean
        get() {
            val fields = parameters!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field == "obspainel") {
                    return true
                }
                ++i
            }
            return false
        }

    fun containsNotes(): Boolean {
        for (item in items!!) {
            if (item.type == 9) {
                return true
            }
        }
        return false
    }

    val ads: ArrayList<Product?>
        get() {
            val adsList = ArrayList<Product?>()
            for (item in items!!) {
                if (item.type == 9 && item.itemProduct!!.pAOCR === 1) {
                    adsList.add(item.itemProduct)
                }
            }
            return adsList
        }
    val notes: ArrayList<Product?>
        get() {
            val list = ArrayList<Product?>()
            for (item in items!!) {
                if (item.type == 9 && item.itemProduct!!.pAOCR === 2) {
                    list.add(item.itemProduct)
                }
            }
            return list
        }
    val adsNotes: ArrayList<Product?>
        get() {
            val list = ArrayList<Product?>()
            for (item in items!!) {
                if ((item.type == 8 || item.type == 9) && (item.itemProduct!!.pAOCR === 1 || item.itemProduct!!.pAOCR === 2)) {
                    list.add(item.itemProduct)
                }
            }
            return list
        }
    val products: ArrayList<Product?>
        get() {
            val list = ArrayList<Product?>()
            for (item in items!!) {
                list.add(item.product)
            }
            return list
        }

    override fun toString(): String {
        return name!!
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
        val other = obj as Product
        return id == other.id
    }

    override fun compareTo(product2: Product?): Int {
        return id - product2!!.id
    }
}
