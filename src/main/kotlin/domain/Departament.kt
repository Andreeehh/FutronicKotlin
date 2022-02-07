package domain


class Department : Comparable<Department?> {
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

    constructor(department: Department) {
        copyFrom(department)
    }

    fun copyFrom(department: Department) {
        id = department.id
        name = department.name
        isActive = department.isActive
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
        val other = obj as Department
        return id == other.id
    }

    override fun compareTo(department2: Department?): Int {
        return id - department2!!.id
    }
}
