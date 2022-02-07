package domain


class Employee : Comparable<Employee?> {
    var id = 0
    var name: String? = null
    var shortName: String? = null
    var gender = 0
    var function: String? = null
    var birthday: String? = null
    var phone: String? = null
    var cellPhone: String? = null
    var state: State? = null
    var city: City? = null
    var streetName: String? = null
    var streetNumber: String? = null
    var zipcode: String? = null
    var neighborhood: Neighborhood? = null
    var idDocNumber: String? = null
    var idDocNumber2: String? = null
    var salary = 0
    var admission: String? = null
    private var department: Department? = null
    var isActive = false

    constructor() {
        id = 0
        name = ""
        shortName = ""
        gender = 1
        function = ""
        birthday = ""
        phone = ""
        cellPhone = ""
        state = State()
        city = City()
        streetName = ""
        streetNumber = ""
        zipcode = ""
        neighborhood = Neighborhood()
        idDocNumber = ""
        idDocNumber2 = ""
        salary = 0
        admission = ""
        department = Department()
        isActive = true
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        shortName = ""
        gender = 1
        function = ""
        birthday = ""
        phone = ""
        cellPhone = ""
        state = State()
        city = City()
        streetName = ""
        streetNumber = ""
        zipcode = ""
        neighborhood = Neighborhood()
        idDocNumber = ""
        idDocNumber2 = ""
        salary = 0
        admission = ""
        department = Department()
        this.isActive = isActive
    }

    constructor(employee: Employee) {
        copyFrom(employee)
    }

    fun copyFrom(employee: Employee) {
        id = employee.id
        name = employee.name
        shortName = employee.shortName
        gender = employee.gender
        function = employee.function
        birthday = employee.birthday
        phone = employee.phone
        cellPhone = employee.cellPhone
        state = employee.state
        city = employee.city
        streetName = employee.streetName
        streetNumber = employee.streetNumber
        zipcode = employee.zipcode
        neighborhood = employee.neighborhood
        idDocNumber = employee.idDocNumber
        idDocNumber2 = employee.idDocNumber2
        salary = employee.salary
        admission = employee.admission
        department = employee.getDepartment()
        isActive = employee.isActive
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

    fun getDepartment(): Department? {
        return department
    }

    fun setDepartment(department: Department?) {
        this.department = department
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
        val other = obj as Employee
        return id == other.id
    }

    override fun compareTo(employee2: Employee?): Int {
        return id - employee2!!.id
    }
}
