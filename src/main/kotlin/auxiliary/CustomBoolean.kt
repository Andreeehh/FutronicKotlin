package auxiliary


class CustomBoolean {
    var boolean = false

    constructor() {
        boolean = false
    }

    constructor(value: Boolean) {
        boolean = value
    }

    constructor(value: Int) {
        if (value == 1) {
            boolean = true
        } else {
            boolean = false
        }
    }

    constructor(value: String) {
        if (value.equals("Sim", ignoreCase = true) || value.equals("true", ignoreCase = true)) {
            boolean = true
        } else {
            boolean = false
        }
    }

    val int: Int
        get() = if (!boolean) {
            0
        } else 1

    override fun toString(): String {
        return if (boolean) {
            "Sim"
        } else "Não"
    }
}
