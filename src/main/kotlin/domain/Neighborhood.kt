package domain


class Neighborhood : Comparable<Neighborhood?> {
    var id = 0
    var name: String? = null
    var city: City? = null
    var value = 0
    var isActive = false

    constructor() {
        id = 0
        name = ""
        city = City()
        value = 0
        isActive = false
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        city = City()
        value = 0
        this.isActive = isActive
    }

    constructor(neighborhood: Neighborhood) {
        copyFrom(neighborhood)
    }

    fun copyFrom(neighborhood: Neighborhood) {
        id = neighborhood.id
        name = neighborhood.name
        city = neighborhood.city
        value = neighborhood.value
        isActive = neighborhood.isActive
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
        val other = obj as Neighborhood
        return id == other.id
    }

    override fun compareTo(neighborhood2: Neighborhood?): Int {
        return id - neighborhood2!!.id
    }
}
