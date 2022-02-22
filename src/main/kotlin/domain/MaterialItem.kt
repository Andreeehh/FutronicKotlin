package domain


class MaterialItem {
    var id = 0
    var rawMaterial: Material? = null
    var department: Department? = null
    var percent = 0.0

    constructor() {
        id = 0
        rawMaterial = Material()
        department = Department()
        percent = 0.0
    }

    constructor(materialItem: MaterialItem) {
        copyFrom(materialItem)
    }

    fun copyFrom(materialItem: MaterialItem) {
        id = materialItem.id
        rawMaterial = materialItem.rawMaterial
        department = materialItem.department
        percent = materialItem.percent
    }

    override fun toString(): String {
        return department!!.name!!
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
        val other = obj as MaterialItem
        return id == other.id
    }
}
