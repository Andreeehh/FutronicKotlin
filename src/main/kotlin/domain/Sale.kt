package domain

import auxiliary.Amount
import auxiliary.CustomDate
import auxiliary.Currency


class Sale : Comparable<Sale?> {
    var id = 0
    var openingDate: String? = null
    var openingTime: String? = null
    var shippingDate: String? = null
    var shippingTime: String? = null
    var departureDate: String? = null
    var departureTime: String? = null
    var deliverDate: String? = null
    var deliverTime: String? = null
    var paymentDate: String? = null
    var paymentTime: String? = null
    var type = 0
    var user: User? = null
    var hallTableId = 0
    var cardId = 0
    var client: Client? = null
    var type2 = 0
    var subClient: SubClient? = null
    var isDelivery = false
    var streetName: String? = null
    var streetNumber: String? = null
    var complement: String? = null
    var zipcode: String? = null
    var coordinates: String? = null
    var neighborhood: Neighborhood? = null
    var phone: String? = null
    var employee: Employee? = null
    var employee2: Employee? = null
    var company: Company? = null
    var representative: String? = null
    var notes: String? = null
    var items: ArrayList<SaleItem>? = null
    var sumValue = 0
    var taxes = 0
    var addition = 0
    var discount = 0
    var payedValue = 0
    var pendentValue = 0
    var value = 0
    var paymentMethod: String? = null
    var changeFor = 0
    var divisor = 0
    var isPayed = false
    var deliverState = 0
    var isPendent = false
    var isPendentItem = false
    private var clickProductionPrint = false
    var scheduleId = 0
    var isCancelled = false
    var fiscal: String? = null
    var sessionCounter = 0
    var sessionId = 0
    var isTaxesRule = false
    var isDiscountRule = false
    var webSaleType = 0
    var webSaleId = 0
    var webSaleCodes: String? = null
    var subDiscount = 0
    var customerExternalId: String? = null
    var isIfoodDelivery = false
    var ifoodDeliveryTaxes = 0
    var ifoodDeliveryDiscount = 0
    var discountReason: String? = null
    var userIdDiscount = 0
    var isIfoodSchedule = false
    var isIfoodIndoor = false
    var isPrintFiscal = false

    constructor() {
        id = 0
        openingDate = ""
        openingTime = ""
        shippingDate = ""
        shippingTime = ""
        departureDate = ""
        departureTime = ""
        deliverDate = ""
        deliverTime = ""
        paymentDate = ""
        paymentTime = ""
        type = 1
        type2 = 0
        isDelivery = false
        streetName = ""
        streetNumber = ""
        complement = ""
        zipcode = ""
        coordinates = ""
        phone = ""
        representative = ""
        notes = ""
        items = ArrayList()
        sumValue = 0
        taxes = 0
        addition = 0
        discount = 0
        value = 0
        changeFor = 0
        paymentMethod = ""
        payedValue = 0
        pendentValue = 0
        divisor = 1
        isPayed = false
        deliverState = 0
        isPendent = false
        isPendentItem = false
        clickProductionPrint = false
        scheduleId = 0
        isCancelled = false
        fiscal = ""
        sessionCounter = 0
        sessionId = 0
        isTaxesRule = true
        isDiscountRule = true
        webSaleType = 0
        webSaleId = 0
        webSaleCodes = ""
        discountReason = ""
        userIdDiscount = 0
        isIfoodSchedule = false
        isIfoodIndoor = false
        isPrintFiscal = false
    }

    constructor(sale: Sale) {
        copyFrom(sale)
    }

    fun copyFrom(sale: Sale) {
        id = sale.id
        openingDate = sale.openingDate
        openingTime = sale.openingTime
        shippingDate = sale.shippingDate
        shippingTime = sale.shippingTime
        departureDate = sale.departureDate
        departureTime = sale.departureTime
        deliverDate = sale.deliverDate
        deliverTime = sale.deliverTime
        paymentDate = sale.paymentDate
        paymentTime = sale.paymentTime
        type = sale.type
        type2 = sale.type2
        user = sale.user
        hallTableId = sale.hallTableId
        cardId = sale.cardId
        client = sale.client
        subClient = sale.subClient
        isDelivery = sale.isDelivery
        streetName = sale.streetName
        streetNumber = sale.streetNumber
        complement = sale.complement
        zipcode = sale.zipcode
        coordinates = sale.coordinates
        neighborhood = sale.neighborhood
        phone = sale.phone
        employee = sale.employee
        employee2 = sale.employee2
        company = sale.company
        representative = sale.representative
        notes = sale.notes
        items = sale.items
        sumValue = sale.sumValue
        taxes = sale.taxes
        addition = sale.addition
        discount = sale.discount
        payedValue = sale.payedValue
        pendentValue = sale.pendentValue
        value = sale.value
        paymentMethod = sale.paymentMethod
        changeFor = sale.changeFor
        divisor = sale.divisor
        isPayed = sale.isPayed
        deliverState = sale.deliverState
        isPendent = sale.isPendent
        isPendentItem = sale.isPendentItem
        clickProductionPrint = sale.clickProductionPrint
        scheduleId = sale.scheduleId
        isCancelled = sale.isCancelled
        fiscal = sale.fiscal
        sessionCounter = sale.sessionCounter
        sessionId = sale.sessionId
        isTaxesRule = sale.isTaxesRule
        isDiscountRule = sale.isDiscountRule
        webSaleType = sale.webSaleType
        webSaleId = sale.webSaleId
        webSaleCodes = sale.webSaleCodes
        discountReason = sale.discountReason
        userIdDiscount = sale.userIdDiscount
        isIfoodSchedule = sale.isIfoodSchedule
        isIfoodIndoor = sale.isIfoodIndoor
        isPrintFiscal = sale.isPrintFiscal
    }

    constructor(list: ArrayList<SaleItem>?) {
        items = list
    }

    val stayTime: String
        get() {
            var fields: Array<String?> = openingTime!!.split(":").toTypedArray()
            val initMin = Integer.valueOf(fields[0]) * 60 + Integer.valueOf(fields[1])
            fields = if (isDelivered) {
                deliverTime!!.split(":").toTypedArray()
            } else {
                CustomDate().timeString.split(":").toTypedArray()
            }
            val endMin = Integer.valueOf(fields[0]) * 60 + Integer.valueOf(fields[1])
            var dif = 0
            dif = if (endMin >= initMin) {
                endMin - initMin
            } else {
                endMin + 1440 - initMin
            }
            return Integer.toString(dif / 60) + " h " + dif % 60 + " min"
        }
    val stayTimeInMinutes: Int
        get() {
            var fields: Array<String?> = openingTime!!.split(":").toTypedArray()
            val initMin = Integer.valueOf(fields[0]) * 60 + Integer.valueOf(fields[1])
            fields = if (isDelivered) {
                deliverTime!!.split(":").toTypedArray()
            } else {
                CustomDate().timeString.split(":").toTypedArray()
            }
            val endMin = Integer.valueOf(fields[0]) * 60 + Integer.valueOf(fields[1])
            var dif = 0
            dif = if (endMin >= initMin) {
                endMin - initMin
            } else {
                endMin + 1440 - initMin
            }
            return dif
        }
    val openingToPaymentTime: String
        get() {
            var fields: Array<String?> = openingTime!!.split(":").toTypedArray()
            val initMin = Integer.valueOf(fields[0]) * 60 + Integer.valueOf(fields[1])
            fields = if (isPayed) {
                paymentTime!!.split(":").toTypedArray()
            } else {
                CustomDate().timeString.split(":").toTypedArray()
            }
            val endMin = Integer.valueOf(fields[0]) * 60 + Integer.valueOf(fields[1])
            var dif = 0
            dif = if (endMin >= initMin) {
                endMin - initMin
            } else {
                endMin + 1440 - initMin
            }
            return Integer.toString(dif / 60) + " h " + dif % 60 + " min"
        }
    val typeString: String
        get() {
            if (type == 1) {
                return "Encomenda"
            }
            if (type == 2) {
                return "Mesa"
            }
            return if (type == 3) {
                "Comanda"
            } else "Geral"
        }

    fun setType(typeString: String) {
        if (typeString.equals("Encomenda", ignoreCase = true)) {
            type = 1
        } else if (typeString.equals("Mesa", ignoreCase = true)) {
            type = 2
        } else if (typeString.equals("Comanda", ignoreCase = true)) {
            type = 3
        } else {
            type = 4
        }
    }

    val name: String?
        get() {
            if (type != 1) {
                if (type == 2) {
                    Integer.toString(hallTableId)
                } else if (type == 3) {
                    Integer.toString(cardId)
                } else if (type == 4) {
                    return representative
                }
                return ""
            }
            return if (client!!.id == 1) {
                representative
            } else client!!.name
        }
    val fullAddress: String?
        get() {
            var adrs = streetName
            if (streetNumber != "") {
                adrs = adrs + "," + streetNumber
            }
            if (complement != "") {
                adrs = adrs + "," + complement
            }
            return adrs
        }
    val appReference: String
        get() = webSaleCodes!!.replace("|", ",").split(",").toTypedArray()[0]
    val itemsHierarchicalOrder: ArrayList<SaleItem>
        get() {
            val groupedItems = ArrayList<SaleItem>()
            for (item in items!!) {
                if (item.fatherItemId != 0) {
                    continue
                }
                groupedItems.add(item)
                for (item2 in items!!) {
                    if (item.id != item2.fatherItemId) {
                        continue
                    }
                    if (item2.type != 1 || item2.getProduct()!!.pAOCR != 1) {
                        continue
                    }
                    groupedItems.add(item2)
                }
                for (item2 in items!!) {
                    if (item.id != item2.fatherItemId) {
                        continue
                    }
                    if (item2.type != 1 || item2.getProduct()!!.pAOCR != 1) {
                        groupedItems.add(item2)
                    }
                    for (item3 in items!!) {
                        if (item2.id != item3.fatherItemId) {
                            continue
                        }
                        groupedItems.add(item3)
                    }
                }
            }
            return groupedItems
        }

    fun getNumItems(type: Int): Int {
        var ret = 0
        if (type == 0) {
            return items!!.size
        }
        if (type == 1) {
            for (item in items!!) {
                if (item.type == 1) {
                    ++ret
                }
            }
        } else if (type == 2) {
            for (item in items!!) {
                if (item.type == 2) {
                    ++ret
                }
            }
        } else if (type == 3) {
            for (item in items!!) {
                if (item.type == 3) {
                    ++ret
                }
            }
        } else if (type == 4) {
            for (item in items!!) {
                if (item.type == 4) {
                    ++ret
                }
            }
        } else if (type == 5) {
            for (item in items!!) {
                if (item.type == 5) {
                    ++ret
                }
            }
        }
        return ret
    }

    fun getItemById(id: Int): SaleItem? {
        for (item in items!!) {
            if (item.id == id) {
                return item
            }
        }
        return null
    }

    fun getFirstComplementById(fatherId: Int): SaleItem? {
        for (item in items!!) {
            if (item.fatherItemId == fatherId) {
                return item
            }
        }
        return null
    }

    val lastItem: SaleItem?
        get() {
            if (items!!.size == 0) {
                return null
            }
            var bigIndex = 0
            for (i in items!!.indices) {
                if (items!![i].id > items!![bigIndex].id) {
                    bigIndex = i
                }
            }
            return items!![bigIndex]
        }
    val lastItemTime: String?
        get() {
            val item = lastItem
            return if (item != null) {
                item.time
            } else openingTime
        }

    fun getItemByProduct(product: Product): SaleItem? {
        for (item in items!!) {
            if (item.getProduct()!!.id == product.id) {
                return item
            }
        }
        return null
    }

    fun getItemByProductTime(product: Product, time: String): SaleItem? {
        var it: SaleItem? = null
        for (item in items!!) {
            if (item.getProduct()!!.id == product.id && item.time == time) {
                it = item
            }
        }
        return it
    }

    fun containsProductCategory(category: Category): Boolean {
        for (item in items!!) {
            if (item.getProduct()!!.category!!.id == category.id) {
                return true
            }
        }
        return false
    }

    fun getProductCategoryAmount(category: Category): Double {
        var ret = 0.0
        if (category.id == 0) {
            for (item in items!!) {
                if (item.type == 1) {
                    ret += item.amount
                }
            }
        } else {
            for (item in items!!) {
                if (item.getProduct()!!.category!!.id == category.id) {
                    ret += item.amount
                }
            }
        }
        return ret
    }

    fun getProductCategoryValue(category: Category): Int {
        var ret = 0
        if (category.id == 0) {
            for (item in items!!) {
                if (item.type == 1) {
                    ret += item.value
                }
            }
        } else {
            for (item in items!!) {
                if (item.getProduct()!!.category!!.id == category.id) {
                    ret += item.value
                }
            }
        }
        return ret
    }

    fun containsProduct(product: Product): Boolean {
        for (item in items!!) {
            if (item.getProduct()!!.id == product.id) {
                return true
            }
        }
        return false
    }

    fun containsService(service: Service): Boolean {
        for (item in items!!) {
            if (item.getService()!!.id == service.id) {
                return true
            }
        }
        return false
    }

    fun getProductAmount(product: Product): Double {
        var ret = 0.0
        if (product.id == 0) {
            for (item in items!!) {
                if (item.type == 1) {
                    ret += item.amount
                }
            }
        } else {
            for (item in items!!) {
                if (item.getProduct()!!.id == product.id) {
                    ret += item.amount
                }
            }
        }
        return ret
    }

    fun getProductValue(product: Product): Int {
        var ret = 0
        if (product.id == 0) {
            for (item in items!!) {
                if (item.type == 1) {
                    ret += item.value
                }
            }
        } else {
            for (item in items!!) {
                if (item.getProduct()!!.id == product.id) {
                    ret += item.value
                }
            }
        }
        return ret
    }

    fun getProductPayedAmount(product: Product): Double {
        var ret = 0.0
        if (product.id == 0) {
            for (item in items!!) {
                if (item.type == 1) {
                    ret += item.payedAmount
                }
            }
        } else {
            for (item in items!!) {
                if (item.getProduct()!!.id == product.id) {
                    ret += item.payedAmount
                }
            }
        }
        return ret
    }

    fun getProductPayedValue(product: Product): Int {
        var ret = 0
        if (product.id == 0) {
            for (item in items!!) {
                if (item.type == 1) {
                    ret += item.payedValue
                }
            }
        } else {
            for (item in items!!) {
                if (item.getProduct()!!.id == product.id) {
                    ret += item.payedValue
                }
            }
        }
        return ret
    }

    fun containsProductWithEquipment(): Boolean {
        for (item in items!!) {
            if (item.type != 1) {
                continue
            }
            for (it in item.getProduct()!!.items!!) {
                if (it.type == 4) {
                    return true
                }
            }
        }
        return false
    }

    fun containsType1Product(): Boolean {
        for (item in items!!) {
            if (item.type != 1) {
                continue
            }
            if (item.getProduct()!!.type == 1) {
                return true
            }
        }
        return false
    }

    fun containsRodizio(): Boolean {
        for (item in items!!) {
            if (item.type == 1 && item.getProduct()!!.pAOCR == 4) {
                return true
            }
        }
        return false
    }

    fun isProductInRodizio(product: Product?): Boolean {
        var rodProduct: Product? = null
        for (item in items!!) {
            if (item.type == 1 && item.getProduct()!!.pAOCR == 4) {
                rodProduct = item.getProduct()
                break
            }
        }
        if (rodProduct == null) {
            return false
        }
        for (item2 in rodProduct.items!!) {
            if (item2.itemProduct!!.equals(product)) {
                return true
            }
        }
        return false
    }

    fun isCategoryInRodizio(category: Category?): Boolean {
        var rodProduct: Product? = null
        for (item in items!!) {
            if (item.type == 1 && item.getProduct()!!.pAOCR == 4) {
                rodProduct = item.getProduct()
                break
            }
        }
        if (rodProduct == null) {
            return false
        }
        for (item2 in rodProduct.items!!) {
            if (item2.itemProduct!!.category!!.equals(category)) {
                return true
            }
        }
        return false
    }

    fun hasProductionGroup(id: Int): Boolean {
        for (item in items!!) {
            if (item.isNOTSentToProduction && (item.getProduct()!!.pAOCR == 0 || item.getProduct()!!.pAOCR == 3)
                && (item.getProduct()!!.productionGroup === id || item.getProduct()!!.productionGroup2 === id)
            ) {
                return true
            }
        }
        return false
    }

    fun hasProductionIdInState(id: Int, state: Int): Boolean {
        for (item in items!!) {
            if (item.productionState == state && item.getProduct()!!.productionGroup === id) {
                return true
            }
        }
        return false
    }

    fun hasProductionPrint(): Boolean {
        for (item in items!!) {
            if (item.isNOTSentToProduction) {
                return true
            }
        }
        return false
    }

    fun hasPendentItem(): Boolean {
        for (item in items!!) {
            if (item.isPendent) {
                return true
            }
        }
        return false
    }

    val sum: Int
        get() {
            var value = 0
            for (item in items!!) {
                value += item.value
            }
            return value
        }

    fun getSum(type: Int): Int {
        var ret = 0
        if (type == 0) {
            return sum
        }
        if (type == 1) {
            for (item in items!!) {
                if (item.type == 1) {
                    ret += item.value
                }
            }
        } else if (type == 2) {
            for (item in items!!) {
                if (item.type == 2) {
                    ret += item.value
                }
            }
        } else if (type == 3) {
            for (item in items!!) {
                if (item.type == 3) {
                    ret += item.value
                }
            }
        }
        return ret
    }

    fun getItemInternalCode(item: SaleItem): String? {
        return if (item.id == 0) {
            ""
        } else item.getProduct()!!.internalCode
    }

    fun getItemName(item: SaleItem): String? {
        if (item.id == 0) {
            return ""
        }
        return if (item.type == 1) {
            if (getFatherItem(item).id != 0 && isCombinedPizzaLeader(getFatherItem(item)) && item.getProduct()!!.pAOCR != 1) {
                val numItems = getCombinedPizzaNumItens(getFatherItem(item).id)
                return "1/" + numItems + " " + item.getProduct()!!.name
            }
            item.getProduct()!!.name
        } else {
            if (item.type == 2) {
                return item.getMaterial()!!.name
            }
            if (item.type == 3) {
                return item.getManufacture()!!.name
            }
            if (item.type == 4) {
                item.getService()!!.name
            } else item.getEquipment()!!.name
        }
    }

    fun getItemAmount(item: SaleItem): String {
        if (item.id == 0) {
            return ""
        }
        if (item.type == 1) {
            if (isCombinedPizzaLeader(item)) {
                return "1"
            }
            if (getFatherItem(item).id != 0 && isCombinedPizzaLeader(getFatherItem(item))) {
                return ""
            }
        }
        return Amount(item.amount).toString()
    }

    fun getItemUnitPrice(item: SaleItem): String {
        if (item.id == 0) {
            return ""
        }
        if (item.type == 1) {
            if (isCombinedPizzaLeader(item)) {
                return Currency(getCombinedPizzaValue(item.id)).toString()
            }
            if (getFatherItem(item).id != 0 && isCombinedPizzaLeader(getFatherItem(item)) && item.getProduct()!!.pAOCR == 0) {
                return ""
            }
        }
        return Currency(item.unitPrice).toString()
    }

    fun getItemValue(item: SaleItem): String {
        if (item.id == 0) {
            return ""
        }
        if (item.type == 1) {
            if (isCombinedPizzaLeader(item)) {
                return Currency(getCombinedPizzaValue(item.id)).toString()
            }
            if (getFatherItem(item).id != 0 && isCombinedPizzaLeader(getFatherItem(item)) && item.getProduct()!!.pAOCR == 0) {
                return ""
            }
            if (item.value == 0) {
                return ""
            }
        }
        return Currency(item.value).toString()
    }

    fun isCombinedPizzaLeader(item: SaleItem): Boolean {
        return item.getProduct()!!.name!!.toLowerCase()
            .contains("pizza") && getFatherItem(item).id == 0 && item.value == 0
    }

    fun getCombinedPizzaNumItens(leaderId: Int): Int {
        var counter = 0
        for (item in items!!) {
            if (getFatherItem(item).id == leaderId && item.getProduct()!!.pAOCR == 0) {
                ++counter
            }
        }
        return counter
    }

    fun getCombinedPizzaValue(leaderId: Int): Int {
        var counter = 0
        for (item in items!!) {
            if (getFatherItem(item).id == leaderId && item.getProduct()!!.pAOCR == 0) {
                counter += item.value
            }
        }
        return counter
    }

    fun getFatherItem(item: SaleItem): SaleItem {
        val fatherItem = getItemById(item.fatherItemId)
        return fatherItem ?: SaleItem()
    }

    fun getGrandFatherItem(item: SaleItem): SaleItem {
        val gFatherItem = getItemById(getFatherItem(item).fatherItemId)
        return gFatherItem ?: SaleItem()
    }

    val acrDesc: Int
        get() = taxes + addition - discount
    val isDelivered: Boolean
        get() = deliverState == 10
    val isShipped: Boolean
        get() = deliverState >= 1
    val isDeparted: Boolean
        get() = deliverState >= 2

    fun setDelivered() {
        deliverState = 10
    }

    override fun toString(): String {
        return deliverDate!!
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
        val other = obj as Sale
        return id == other.id
    }

    override fun compareTo(sale2: Sale?): Int {
        return id - sale2!!.id
    }
}
