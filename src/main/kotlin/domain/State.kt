package domain


class State : Comparable<State?> {
    var id = 0
    var name: String? = null
    var auxName: String? = null
    var isActive = false

    constructor() {
        id = 0
        name = ""
        auxName = ""
        isActive = false
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        auxName = ""
        this.isActive = isActive
    }

    constructor(state: State) {
        copyFrom(state)
    }

    fun copyFrom(state: State) {
        id = state.id
        name = state.name
        auxName = state.auxName
        isActive = state.isActive
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
        val other = obj as State
        return id == other.id
    }

    override fun compareTo(state2: State?): Int {
        return id - state2!!.id
    }
}
