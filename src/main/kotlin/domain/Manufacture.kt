package domain


class Manufacture : Comparable<Manufacture?> {
    var id = 0
    var name: String? = null
    var amount = 0.0
    var isActive = false

    constructor() {
        id = 0
        name = ""
        amount = 0.0
        isActive = false
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        amount = 0.0
        this.isActive = isActive
    }

    constructor(manufacture: Manufacture) {
        copyFrom(manufacture)
    }

    fun copyFrom(manufacture: Manufacture) {
        id = manufacture.id
        name = manufacture.name
        amount = manufacture.amount
        isActive = manufacture.isActive
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
        val other = obj as Manufacture
        return id == other.id
    }

    override fun compareTo(manufacture2: Manufacture?): Int {
        return id - manufacture2!!.id
    }
}
