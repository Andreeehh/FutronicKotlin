package domain


class Supplier : Comparable<Supplier?> {
    var id = 0
    var name: String? = null
    var auxName: String? = null
    var phone: String? = null
    var cellPhone: String? = null
    var email: String? = null
    var state: State? = null
    var city: City? = null
    var streetName: String? = null
    var streetNumber: String? = null
    var zipcode: String? = null
    var neighborhood: Neighborhood? = null
    var idDocNumber1: String? = null
    var idDocNumber2: String? = null
    var idDocNumber3: String? = null
    var notes: String? = null
    var isActive = false
    var ie: String? = null

    constructor() {
        id = 0
        name = ""
        auxName = ""
        phone = ""
        cellPhone = ""
        email = ""
        state = State()
        city = City()
        streetName = ""
        streetNumber = ""
        zipcode = ""
        neighborhood = Neighborhood()
        idDocNumber1 = ""
        idDocNumber2 = ""
        idDocNumber3 = ""
        notes = ""
        ie = ""
        isActive = false
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        auxName = ""
        phone = ""
        cellPhone = ""
        email = ""
        state = State()
        city = City()
        streetName = ""
        streetNumber = ""
        zipcode = ""
        neighborhood = Neighborhood()
        idDocNumber1 = ""
        idDocNumber2 = ""
        idDocNumber3 = ""
        notes = ""
        ie = ""
        this.isActive = isActive
    }

    constructor(supplier: Supplier) {
        copyFrom(supplier)
    }

    fun copyFrom(supplier: Supplier) {
        id = supplier.id
        name = supplier.name
        auxName = supplier.auxName
        phone = supplier.phone
        cellPhone = supplier.cellPhone
        email = supplier.email
        state = supplier.state
        city = supplier.city
        streetName = supplier.streetName
        streetNumber = supplier.streetNumber
        zipcode = supplier.zipcode
        neighborhood = supplier.neighborhood
        idDocNumber1 = supplier.idDocNumber1
        idDocNumber2 = supplier.idDocNumber2
        idDocNumber3 = supplier.idDocNumber3
        notes = supplier.notes
        notes = supplier.ie
        isActive = supplier.isActive
    }

    val streetNameNumber: String?
        get() = if (streetNumber == "") {
            streetName
        } else streetName + ", " + streetNumber

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
        val other = obj as Supplier
        return id == other.id
    }

    override fun compareTo(provider2: Supplier?): Int {
        return id - provider2!!.id
    }
}
