package domain
import auxiliary.CustomDate
import java.sql.Blob


class Client : Comparable<Client?> {
    var id = 0
    var name: String? = null
    var docName: String? = null
    var ownerName: String? = null
    var type1 = 0
    var type2 = 0
    var gender = 0
    var occupation: String? = null
    var birthday: String? = null
    var phone: String? = null
    var cellPhone: String? = null
    var phone2: String? = null
    var cellPhone2: String? = null
    var email: String? = null
    var isSendNotifications = false
    private var state: State? = null
    private var city: City? = null
    var streetName: String? = null
    var streetNumber: String? = null
    var zipcode: String? = null
    private var neighborhood: Neighborhood? = null
    var complement: String? = null
    var idDocNumber: String? = null
    var idDocNumber2: String? = null
    var idDocNumber3: String? = null
    var idDocNumber4: String? = null
    var idDocNumber5: String? = null
    private var company: Company? = null
    var notes: String? = null
    var isAccount = false
    var coordinates: String? = null
    var expirationDay = 0
    var expirationMonthOffset = 0
    var clientCreditLimit = 0
    var companyCreditLimit = 0
    var standardValue = 0
    var taxes = 0
    var credits = 0
    var value4 = 0
    var register: String? = null
    private var employee: Employee? = null
    var payments: String? = null
    var isActive = false
    var externalId: String? = null
    private var fingerprint: Blob? = null

    constructor() {
        id = 0
        name = ""
        docName = ""
        ownerName = ""
        type1 = 0
        type2 = 0
        gender = 1
        occupation = ""
        birthday = ""
        phone = ""
        cellPhone = ""
        phone2 = ""
        cellPhone2 = ""
        email = ""
        isSendNotifications = false
        state = State()
        city = City()
        streetName = ""
        streetNumber = ""
        zipcode = ""
        neighborhood = Neighborhood()
        complement = ""
        idDocNumber = ""
        idDocNumber2 = ""
        idDocNumber3 = ""
        idDocNumber4 = ""
        idDocNumber5 = ""
        company = Company()
        notes = ""
        isAccount = false
        coordinates = ""
        expirationDay = 0
        expirationMonthOffset = 0
        clientCreditLimit = 0
        companyCreditLimit = 0
        standardValue = 0
        taxes = 0
        credits = 0
        value4 = 0
        register = ""
        employee = Employee()
        payments = ""
        isActive = true
        externalId = ""
        fingerprint = null
    }

    constructor(id: Int, name: String?, isActive: Boolean) {
        this.id = id
        this.name = name
        docName = ""
        ownerName = ""
        type1 = 0
        type2 = 0
        gender = 1
        occupation = ""
        birthday = ""
        phone = ""
        cellPhone = ""
        phone2 = ""
        cellPhone2 = ""
        email = ""
        isSendNotifications = false
        state = State()
        city = City()
        streetName = ""
        streetNumber = ""
        zipcode = ""
        neighborhood = Neighborhood()
        complement = ""
        idDocNumber = ""
        idDocNumber2 = ""
        idDocNumber3 = ""
        idDocNumber4 = ""
        idDocNumber5 = ""
        company = Company()
        notes = ""
        isAccount = false
        coordinates = ""
        expirationDay = 0
        expirationMonthOffset = 0
        clientCreditLimit = 0
        companyCreditLimit = 0
        standardValue = 0
        taxes = 0
        credits = 0
        value4 = 0
        register = ""
        employee = Employee()
        payments = ""
        this.isActive = isActive
        externalId = ""
        fingerprint = null
    }

    constructor(client: Client) {
        copyFrom(client)
    }

    fun copyFrom(client: Client) {
        id = client.id
        name = client.name
        docName = client.docName
        ownerName = client.ownerName
        type1 = client.type1
        type2 = client.type2
        gender = client.gender
        occupation = client.occupation
        birthday = client.birthday
        phone = client.phone
        cellPhone = client.cellPhone
        phone2 = client.phone2
        cellPhone2 = client.cellPhone2
        email = client.email
        isSendNotifications = client.isSendNotifications
        state = client.getState()
        city = client.getCity()
        streetName = client.streetName
        streetNumber = client.streetNumber
        zipcode = client.zipcode
        neighborhood = client.getNeighborhood()
        complement = client.complement
        idDocNumber = client.idDocNumber
        idDocNumber2 = client.idDocNumber2
        idDocNumber3 = client.idDocNumber3
        idDocNumber4 = client.idDocNumber4
        idDocNumber5 = client.idDocNumber5
        company = client.getCompany()
        notes = client.notes
        isAccount = client.isAccount
        coordinates = client.coordinates
        expirationDay = client.expirationDay
        expirationMonthOffset = client.expirationMonthOffset
        clientCreditLimit = client.clientCreditLimit
        companyCreditLimit = client.companyCreditLimit
        standardValue = client.standardValue
        register = client.register
        employee = client.getEmployee()
        payments = client.payments
        isActive = client.isActive
        externalId = client.externalId
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

    fun isBirthday(date: CustomDate): Boolean {
        val date2 = CustomDate(birthday)
        return date.dayMonthString == date2.dayMonthString && this.birthday != "01/01/1950"
    }

    val phones: String
        get() = phone + "  " + cellPhone + "  " + phone2 + "  " + cellPhone2

    fun getState(): State? {
        return state
    }

    fun setState(state: State?) {
        this.state = state
    }

    fun getCity(): City? {
        return city
    }

    fun setCity(city: City?) {
        this.city = city
    }

    val streetNameNumber: String?
        get() {
            return if ((streetNumber == "")) {
                streetName
            } else streetName + ", " + streetNumber
        }
    val fullAddress: String?
        get() {
            var ret = streetNameNumber
            if (complement != "") {
                ret = ret + "," + complement
            }
            if (notes != "") {
                ret = ret + "," + notes
            }
            return ret
        }

    fun getNeighborhood(): Neighborhood? {
        return neighborhood
    }

    fun setNeighborhood(neighborhood: Neighborhood?) {
        this.neighborhood = neighborhood
    }

    fun getCompany(): Company? {
        return company
    }

    fun setCompany(company: Company?) {
        this.company = company
    }

    fun getEmployee(): Employee? {
        return employee
    }

    fun setEmployee(employee: Employee?) {
        this.employee = employee
    }

    fun isPermittedPayment(index: Int): Boolean {
        return payments == "" || payments!!.substring(index, index + 1) == "1"
    }

    fun setPermittedPayment(index: Int, value: Boolean) {
        if (payments == "") {
            return
        }
        val temp = StringBuilder(payments)
        temp.setCharAt(index, if (value) '1' else '0')
        payments = temp.toString()
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
        val other = obj as Client
        return id == other.id
    }

    override fun compareTo(client2: Client?): Int {
        return id - client2!!.id
    }
}
