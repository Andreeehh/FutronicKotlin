package domain

import java.math.BigDecimal
import auxiliary.Amount


class SaleItem : Comparable<SaleItem?> {
    var id = 0
    var saleId = 0
    var user: User? = null
    var time: String? = null
    var type = 0
    private var product: Product? = null
    private var material: Material? = null
    private var manufacture: Manufacture? = null
    private var service: Service? = null
    private var equipment: Equipment? = null
    var unitPrice = 0
    var amount = 0.0
    var value = 0
    var notes: String? = null
    var productionState = 0
    var productionBatch = 0
    var isPendent = false
    var payedAmount = 0.0
    var payedValue = 0
    var fatherItemId = 0
    var isCancelled = false
    var fatherItem: SaleItem? = null
    var isWebSale = false
    var valRat = 0.0
    var transferredFrom = 0

    constructor() {
        id = 0
        saleId = 0
        user = User()
        time = ""
        type = 0
        product = Product()
        material = Material()
        manufacture = Manufacture()
        service = Service()
        equipment = Equipment()
        unitPrice = 0
        amount = 0.0
        value = 0
        notes = ""
        productionState = 0
        productionBatch = 0
        isPendent = false
        payedAmount = 0.0
        payedValue = 0
        fatherItemId = 0
        isCancelled = false
        valRat = 0.0
        transferredFrom = 0
    }

    constructor(saleItem: SaleItem) {
        copyFrom(saleItem)
    }

    fun copyFrom(saleItem: SaleItem) {
        id = saleItem.id
        saleId = saleItem.saleId
        user = saleItem.user
        time = saleItem.time
        type = saleItem.type
        product = saleItem.product
        material = saleItem.material
        manufacture = saleItem.manufacture
        service = saleItem.service
        equipment = saleItem.equipment
        unitPrice = saleItem.unitPrice
        amount = saleItem.amount
        value = saleItem.value
        notes = saleItem.notes
        productionState = saleItem.productionState
        productionBatch = saleItem.productionBatch
        isPendent = saleItem.isPendent
        payedAmount = saleItem.payedAmount
        payedValue = saleItem.payedValue
        fatherItemId = saleItem.fatherItemId
        isCancelled = saleItem.isCancelled
        valRat = saleItem.valRat
        transferredFrom = saleItem.transferredFrom
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
                "Serviço"
            } else "Equipamento"
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
        } else if (typeString.equals("Serviço", ignoreCase = true)) {
            type = 4
        } else {
            type = 5
        }
    }

    val name: String
        get() {
            if (type == 1) {
                return getProduct()!!.name!!
            }
            if (type == 2) {
                return getMaterial()!!.name!!
            }
            if (type == 3) {
                return getManufacture()!!.name!!
            }
            if (type == 4) {
                return getService()!!.name!!
            }
            return if (type == 5) {
                getEquipment()!!.name!!
            } else ""
        }

    fun getProduct(): Product? {
        return product
    }

    fun setProduct(product: Product?) {
        this.product = product
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

    fun getService(): Service? {
        return service
    }

    fun setService(service: Service?) {
        this.service = service
    }

    fun getEquipment(): Equipment? {
        return equipment
    }

    fun setEquipment(equipment: Equipment?) {
        this.equipment = equipment
    }

    val measureUnit: String
        get() {
            if (type == 1) {
                return product!!.measureUnit!!
            }
            return if (type == 2) {
                material!!.measureUnit!!
            } else "u"
        }

    fun calculateValue() {
        val amountBig = BigDecimal(Amount(amount).toString4Decimals())
        val valueBig: BigDecimal = amountBig.multiply(BigDecimal(unitPrice))
        value = valueBig.setScale(0, 6).toInt()
    }

    fun calculateItemAmount() {
        val value = value.toDouble()
        amount = value / unitPrice
    }

    fun getNotesBreakLines(numChars: Int): String {
        val sb = StringBuilder(notes)
        for (i in 1 until notes!!.length) {
            if (i % numChars == 0) {
                sb.insert(i, "<br>")
            }
        }
        return sb.toString()
    }

    fun addNotes(notes: String) {
        if (this.notes == "") {
            this.notes = notes
        } else {
            this.notes = this.notes + "," + notes
        }
    }

    var isNOTSentToProduction: Boolean
        get() = productionState == 10
        set(isNOTSent) {
            if (isNOTSent) {
                productionState = 10
            } else {
                productionState = 0
            }
        }

    override fun toString(): String {
        return time!!
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
        val other = obj as SaleItem
        return id == other.id
    }

    override fun compareTo(item: SaleItem?): Int {
        return id - item!!.id
    }
}
