package domain


class City : Comparable<City?> {
    var id = 0
    var name: String? = null
    var code: String? = null
    var state: State? = null
    var referencedCityId = 0
    var isActive = false

    constructor() {
        id = 0
        name = ""
        this.code = ""
        state = State()
        referencedCityId = 0
        isActive = false
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        this.code = ""
        state = State()
        referencedCityId = 0
        this.isActive = isActive
    }

    constructor(city: City) {
        copyFrom(city)
    }

    fun copyFrom(city: City) {
        id = city.id
        name = city.name
        this.code = city.code
        state = city.state
        referencedCityId = city.referencedCityId
        isActive = city.isActive
    }

    val cityState: String
        get() = name + " - " + state!!.auxName

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
        val other = obj as City
        return id == other.id
    }

    override fun compareTo(city2: City?): Int {
        return id - city2!!.id
    }
}
