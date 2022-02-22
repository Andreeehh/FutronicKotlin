package domain


class User : Comparable<User?> {
    var id = 0
    var name: String? = null
    var password: String? = null
    var permissions01: String? = null
    var permissions02: String? = null
    var permissions03: String? = null
    var permissions04: String? = null
    var permissions05: String? = null
    var permissions06: String? = null
    var permissions07: String? = null
    var permissions08: String? = null
    var permissions09: String? = null
    var permissions10: String? = null
    var permissions11: String? = null
    var permissions12: String? = null
    var permissions13: String? = null
    var permissions14: String? = null
    var permissions15: String? = null
    var permissions16: String? = null
    var permissions17: String? = null
    var permissions18: String? = null
    var permissions19: String? = null
    var permissions20: String? = null
    var permissions21: String? = null
    var permissions22: String? = null
    var permissions23: String? = null
    var permissions24: String? = null
    var permissions25: String? = null
    var permissions26: String? = null
    var permissions27: String? = null
    var permissions28: String? = null
    var permissions29: String? = null
    var permissions30: String? = null
    var permissions31: String? = null
    var permissions32: String? = null
    var permissions33: String? = null
    var permissions34: String? = null
    var permissions35: String? = null
    var permissions36: String? = null
    var permissions37: String? = null
    var permissions38: String? = null
    var permissions39: String? = null
    var permissions40: String? = null
    var permissionsGeneral: String? = null
    var isActive = false

    constructor() {
        id = 0
        name = ""
        password = ""
        permissions01 = "11111"
        permissions02 = "11111"
        permissions03 = "11111"
        permissions04 = "11111"
        permissions05 = "11111"
        permissions06 = "11111"
        permissions07 = "11111"
        permissions08 = "11111"
        permissions09 = "11111"
        permissions10 = "11111"
        permissions11 = "11111"
        permissions12 = "11111"
        permissions13 = "11111"
        permissions14 = "11111"
        permissions15 = "11111"
        permissions16 = "11111"
        permissions17 = "11111"
        permissions18 = "11111"
        permissions19 = "11111"
        permissions20 = "11111"
        permissions21 = "11111"
        permissions22 = "11111"
        permissions23 = "11111"
        permissions24 = "11111"
        permissions25 = "11111"
        permissions26 = "11111"
        permissions27 = "11111"
        permissions28 = "11111"
        permissions29 = "11111"
        permissions30 = "11111"
        permissions31 = "11111"
        permissions32 = "11111"
        permissions33 = "11111"
        permissions34 = "11111"
        permissions35 = "11111"
        permissions36 = "11111"
        permissions37 = "11111"
        permissions38 = "11111"
        permissions39 = "11111"
        permissions40 = "11111"
        permissionsGeneral = "11111111111111111111"
        isActive = true
    }

    constructor(id: Int, name: String?, password: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        this.password = password
        permissions01 = "11111"
        permissions02 = "11111"
        permissions03 = "11111"
        permissions04 = "11111"
        permissions05 = "11111"
        permissions06 = "11111"
        permissions07 = "11111"
        permissions08 = "11111"
        permissions09 = "11111"
        permissions10 = "11111"
        permissions11 = "11111"
        permissions12 = "11111"
        permissions13 = "11111"
        permissions14 = "11111"
        permissions15 = "11111"
        permissions16 = "11111"
        permissions17 = "11111"
        permissions18 = "11111"
        permissions19 = "11111"
        permissions20 = "11111"
        permissions21 = "11111"
        permissions22 = "11111"
        permissions23 = "11111"
        permissions24 = "11111"
        permissions25 = "11111"
        permissions26 = "11111"
        permissions27 = "11111"
        permissions28 = "11111"
        permissions29 = "11111"
        permissions30 = "11111"
        permissions31 = "11111"
        permissions32 = "11111"
        permissions33 = "11111"
        permissions34 = "11111"
        permissions35 = "11111"
        permissions36 = "11111"
        permissions37 = "11111"
        permissions38 = "11111"
        permissions39 = "11111"
        permissions40 = "11111"
        permissionsGeneral = "11111111111111111111"
        this.isActive = isActive
    }

    constructor(user: User) {
        copyFrom(user)
    }

    fun copyFrom(user: User) {
        id = user.id
        name = user.name
        password = user.password
        permissions01 = user.permissions01
        permissions02 = user.permissions02
        permissions03 = user.permissions03
        permissions04 = user.permissions04
        permissions05 = user.permissions05
        permissions06 = user.permissions06
        permissions07 = user.permissions07
        permissions08 = user.permissions08
        permissions09 = user.permissions09
        permissions10 = user.permissions10
        permissions11 = user.permissions11
        permissions12 = user.permissions12
        permissions13 = user.permissions13
        permissions14 = user.permissions14
        permissions15 = user.permissions15
        permissions16 = user.permissions16
        permissions17 = user.permissions17
        permissions18 = user.permissions18
        permissions19 = user.permissions19
        permissions20 = user.permissions20
        permissions21 = user.permissions21
        permissions22 = user.permissions22
        permissions23 = user.permissions23
        permissions24 = user.permissions24
        permissions25 = user.permissions25
        permissions26 = user.permissions26
        permissions27 = user.permissions27
        permissions28 = user.permissions28
        permissions29 = user.permissions29
        permissions30 = user.permissions30
        permissions31 = user.permissions31
        permissions32 = user.permissions32
        permissions33 = user.permissions33
        permissions34 = user.permissions34
        permissions35 = user.permissions35
        permissions36 = user.permissions36
        permissions37 = user.permissions37
        permissions38 = user.permissions38
        permissions39 = user.permissions39
        permissions40 = user.permissions40
        permissionsGeneral = user.permissionsGeneral
        isActive = user.isActive
    }

    var isSaleType1: Boolean
        get() = permissionsGeneral!!.substring(0, 1) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(0, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSaleType2: Boolean
        get() = permissionsGeneral!!.substring(1, 2) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(1, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSaleType3: Boolean
        get() = permissionsGeneral!!.substring(2, 3) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(2, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSaleType4: Boolean
        get() = permissionsGeneral!!.substring(3, 4) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(3, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSaleBill: Boolean
        get() = permissionsGeneral!!.substring(4, 5) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(4, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSaleTransfer: Boolean
        get() = permissionsGeneral!!.substring(5, 6) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(5, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSessionView: Boolean
        get() = permissionsGeneral!!.substring(10, 11) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(10, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSessionSale: Boolean
        get() = permissionsGeneral!!.substring(11, 12) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(11, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSessionFinance: Boolean
        get() = permissionsGeneral!!.substring(12, 13) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(12, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSessionFinish: Boolean
        get() = permissionsGeneral!!.substring(13, 14) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(13, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
        }
    var isSessionReview: Boolean
        get() = permissionsGeneral!!.substring(14, 15) == "1"
        set(value) {
            val sb = StringBuilder(permissionsGeneral)
            sb.setCharAt(14, if (value) '1' else '0')
            permissionsGeneral = sb.toString()
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
        val other = obj as User
        return id == other.id
    }

    override fun compareTo(user2: User?): Int {
        return id - user2!!.id
    }
}
