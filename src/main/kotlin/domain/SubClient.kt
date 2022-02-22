package domain


class SubClient : Comparable<SubClient?>{
    var id: Int
    var name: String
    var client: Client
    var gender: Int
    var birthday: String
    var register: String
    var isActive: Boolean
    private var brand: Brand
    var entity2_id: Int
    var type1: Int
    var type2: Int
    var type3: Int
    var type4: Int
    var description1: String
    var description2: String
    var description3: String
    var description4: String
    var date1: String
    var date2: String
    var date3: String
    var date4: String
    var isIs1: Boolean
        private set
    var isIs2: Boolean
        private set
    var isIs3: Boolean
        private set
    var isIs4: Boolean
        private set
    var amount1: Double
    var amount2: Double
    var amount3: Double
    var defaultValue: Int

    constructor() {
        id = 0
        name = ""
        client = Client()
        gender = 1
        birthday = ""
        register = ""
        isActive = true
        brand = Brand()
        entity2_id = 0
        type1 = 1
        type2 = 1
        type3 = 1
        type4 = 1
        description1 = ""
        description2 = ""
        description3 = ""
        description4 = ""
        date1 = ""
        date2 = ""
        date3 = ""
        date4 = ""
        isIs1 = false
        isIs2 = false
        isIs3 = false
        isIs4 = false
        amount1 = 0.0
        amount2 = 0.0
        amount3 = 0.0
        defaultValue = 0
    }

    constructor(id: Int, name: String, active: Boolean) {
        this.id = id
        this.name = name
        client = Client()
        gender = 1
        birthday = ""
        register = ""
        isActive = active
        brand = Brand()
        entity2_id = 0
        type1 = 1
        type2 = 1
        type3 = 1
        type4 = 1
        description1 = ""
        description2 = ""
        description3 = ""
        description4 = ""
        date1 = ""
        date2 = ""
        date3 = ""
        date4 = ""
        isIs1 = false
        isIs2 = false
        isIs3 = false
        isIs4 = false
        amount1 = 0.0
        amount2 = 0.0
        amount3 = 0.0
        defaultValue = 0
    }

    fun copyFrom(subClient: SubClient) {
        id = subClient.id
        name = subClient.name
        client = subClient.client
        gender = subClient.gender
        birthday = subClient.birthday
        register = subClient.register
        isActive = subClient.isActive
        brand = subClient.brand
        entity2_id = subClient.entity2_id
        type1 = subClient.type1
        type2 = subClient.type2
        type3 = subClient.type3
        type4 = subClient.type4
        description1 = subClient.description1
        description2 = subClient.description2
        description3 = subClient.description3
        description4 = subClient.description4
        date1 = subClient.date1
        date2 = subClient.date2
        date3 = subClient.date3
        date4 = subClient.date4
        isIs1 = subClient.isIs1
        isIs2 = subClient.isIs2
        isIs3 = subClient.isIs3
        isIs4 = subClient.isIs4
        amount1 = subClient.amount1
        amount2 = subClient.amount2
        amount3 = subClient.amount3
        defaultValue = subClient.defaultValue
    }

    val genderString: String
        get() = if (gender == 1) {
            "Masculino"
        } else "Feminino"

    fun setGender(genderString: String) {
        if (genderString.equals("Masculino", ignoreCase = true)) {
            gender = 1
        } else {
            gender = 2
        }
    }

    fun getBrand(): Brand {
        return brand
    }

    fun setBrand(brand: Brand) {
        this.brand = brand
    }

    fun setIs1(is1: Boolean) {
        isIs1 = is1
    }

    fun setIs2(is2: Boolean) {
        isIs2 = is2
    }

    fun setIs3(is3: Boolean) {
        isIs3 = is3
    }

    fun setIs4(is4: Boolean) {
        isIs4 = is4
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
        val other = obj as SubClient
        return id == other.id
    }

    override fun compareTo(subClient2: SubClient?): Int {
        return id - subClient2!!.id
    }
}
