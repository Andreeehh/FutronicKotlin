package domain

import java.time.LocalTime
import java.util.*


class Category : Comparable<Category?> {
    var id = 0
    var name: String? = null
    var description: String? = null
    var parameters: String? = null
    var type = 0
    var subType = 0
    var panel = 0
    private var panelName: String? = null
    var panelPosition = 0
    var panelOrderElements = 0
    var webAuto = 0
    var fatherId = 0
    var isFatherCategory = false
    var isActive = false
    var isApplyTaxes = false

    constructor() {
        id = 0
        name = ""
        description = ""
        parameters = ""
        type = 1
        subType = 1
        panel = 0
        panelName = ""
        panelPosition = 0
        panelOrderElements = 0
        webAuto = 0
        isActive = false
        fatherId = 0
        isFatherCategory = false
        isApplyTaxes = true
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        description = ""
        parameters = ""
        type = 1
        subType = 1
        panel = 0
        panelName = ""
        panelPosition = 0
        panelOrderElements = 0
        webAuto = 0
        this.isActive = isActive
        isApplyTaxes = true
    }

    constructor(category: Category) {
        copyFrom(category)
    }

    fun copyFrom(category: Category) {
        id = category.id
        name = category.name
        description = category.description
        parameters = category.parameters
        type = category.type
        subType = category.subType
        panel = category.panel
        panelName = category.getPanelName()
        panelPosition = category.panelPosition
        panelOrderElements = category.panelOrderElements
        webAuto = category.webAuto
        isActive = category.isActive
        isApplyTaxes = category.isApplyTaxes
    }

    val nameCheckType: String?
        get() = if (name!!.toLowerCase().contains("pizza")) {
            pizzaName
        } else name
    val pizzaName: String?
        get() {
            val name = name
            val fields = description!!.split(",").toTypedArray()
            if (name!!.endsWith("GG")) {
                var array: Array<String>
                val length = fields.also { array = it }.size
                var i = 0
                while (i < length) {
                    val field = array[i]
                    if (field.contains("PizzaGG")) {
                        return name + " (" + field.replace("PizzaGG", "") + ")"
                    }
                    ++i
                }
            }
            if (name.endsWith("G")) {
                var array2: Array<String>
                val length2 = fields.also { array2 = it }.size
                var j = 0
                while (j < length2) {
                    val field = array2[j]
                    if (field.contains("PizzaG")) {
                        return name + " (" + field.replace("PizzaG", "") + ")"
                    }
                    ++j
                }
            }
            if (name.endsWith("M")) {
                var array3: Array<String>
                val length3 = fields.also { array3 = it }.size
                var k = 0
                while (k < length3) {
                    val field = array3[k]
                    if (field.contains("PizzaM")) {
                        return name + " (" + field.replace("PizzaM", "") + ")"
                    }
                    ++k
                }
            }
            if (name.endsWith("P")) {
                var array4: Array<String>
                val length4 = fields.also { array4 = it }.size
                var l = 0
                while (l < length4) {
                    val field = array4[l]
                    if (field.contains("PizzaP")) {
                        return name + " (" + field.replace("PizzaP", "") + ")"
                    }
                    ++l
                }
            }
            return name
        }
    val sortBy: String
        get() {
            val ret = "alfa"
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("painelordem")) {
                    return field.split("=").toTypedArray()[1]
                }
                ++i
            }
            return ret
        }
    val isEditRule: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("regra")) {
                    return true
                }
                ++i
            }
            return false
        }
    val isInTime: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("hora")) {
                    val initS = field.split("=").toTypedArray()[1].split("-").toTypedArray()[0]
                    val endS = field.split("=").toTypedArray()[1].split("-").toTypedArray()[1]
                    val init = LocalTime.of(
                        initS.split(":").toTypedArray()[0].toInt(),
                        initS.split(":").toTypedArray()[1].toInt()
                    )
                    val end = LocalTime.of(
                        endS.split(":").toTypedArray()[0].toInt(),
                        endS.split(":").toTypedArray()[1].toInt()
                    )
                    if (LocalTime.now().compareTo(init) < 0 || LocalTime.now().compareTo(end) > 0) {
                        return false
                    }
                }
                ++i
            }
            return true
        }
    val isInDay: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("dia")) {
                    val days = field.split("=").toTypedArray()[1]
                    val currentDay = GregorianCalendar()[Calendar.DAY_OF_WEEK]
                    if (checkIntDayByArrayDays(days, currentDay)) {
                        return true
                    }
                } else {
                    return true
                }
                ++i
            }
            return false
        }

    private fun checkIntDayByArrayDays(arrayDays: String, currentDay: Int): Boolean {
        return if (arrayDays.contains("dom") && currentDay == 1) {
            true
        } else if (arrayDays.contains("seg") && currentDay == 2) {
            true
        } else if (arrayDays.contains("ter") && currentDay == 3) {
            true
        } else if (arrayDays.contains("qua") && currentDay == 4) {
            true
        } else if (arrayDays.contains("qui") && currentDay == 5) {
            true
        } else if (arrayDays.contains("sex") && currentDay == 6) {
            true
        } else arrayDays.contains("sab") && currentDay == 7
    }

    val isCategoryRadio: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("radio")) {
                    return true
                }
                ++i
            }
            return false
        }
    val isAmounts: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("amounts")) {
                    return true
                }
                ++i
            }
            return false
        }
    val isCategoryMax1: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("max1")) {
                    return true
                }
                ++i
            }
            return false
        }

    fun showPriceInPanel(): Boolean {
        val fields = description!!.split(",").toTypedArray()
        var array: Array<String>
        val length = fields.also { array = it }.size
        var i = 0
        while (i < length) {
            val field = array[i]
            if (field.contains("painelpreco")) {
                return true
            }
            ++i
        }
        return false
    }

    fun getCurrentStock(list: Vector<Product>): Double {
        var amount = 0.0
        for (product in list) {
            if (product.category!!.id === id) {
                amount += product.currentStock
            }
        }
        return amount
    }

    val isPizza: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("pizza")) {
                    return true
                }
                ++i
            }
            return false
        }
    val pizzaMaxFlavors: Int
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("pizza")) {
                    return field.split("=").toTypedArray()[1].toInt()
                }
                ++i
            }
            return 0
        }
    val isBorda: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("borda")) {
                    return true
                }
                ++i
            }
            return false
        }
    val isRadio: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("radio")) {
                    return true
                }
                ++i
            }
            return false
        }
    val isItemNotes: Boolean
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("itemobs")) {
                    return true
                }
                ++i
            }
            return false
        }
    val itemObsLabel: String
        get() {
            val fields = description!!.split(",").toTypedArray()
            var array: Array<String>
            val length = fields.also { array = it }.size
            var i = 0
            while (i < length) {
                val field = array[i]
                if (field.contains("itemobs")) {
                    return field.split("=").toTypedArray()[1]
                }
                ++i
            }
            return ""
        }
    val typeString: String
        get() {
            if (type == 1) {
                return "Produto"
            }
            if (type == 2) {
                return "Serviço"
            }
            return if (type == 3) {
                "Materiais"
            } else "Equipamento"
        }
    val isAuto: Boolean
        get() = webAuto == 1 || webAuto == 3
    val isWeb: Boolean
        get() = webAuto == 1 || webAuto == 2

    fun getPanelName(): String? {
        return if (!(panelName == "")) {
            panelName
        } else name
    }

    fun setPanelName(panelName: String?) {
        this.panelName = panelName
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
        val other = obj as Category
        return id == other.id
    }

    val isChild: Boolean
        get() = fatherId > 0

    override fun compareTo(productCategory2: Category?): Int {
        return id - productCategory2!!.id
    }
}
