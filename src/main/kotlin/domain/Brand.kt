package domain


class Brand : Comparable<Brand?> {
    var id = 0
    var name: String? = null
    var isActive = false

    constructor() {
        id = 0
        name = ""
        isActive = false
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        this.isActive = isActive
    }

    constructor(brand: Brand) {
        copyFrom(brand)
    }

    fun copyFrom(brand: Brand) {
        id = brand.id
        name = brand.name
        isActive = brand.isActive
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
        val other = obj as Brand
        return id == other.id
    }

    override fun compareTo(brand2: Brand?): Int {
        return id - brand2!!.id
    }
}
