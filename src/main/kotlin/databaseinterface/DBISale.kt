package databaseinterface

import auxiliary.Amount
import auxiliary.CustomBoolean
import domain.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class DBISale {
    val banco = "jdbc:mysql://localhost:3306/db000"

    var con: Connection? = null
    fun getUpdateNextId(): Int {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        var id = 0
        val stmt = "select sale_id from next_id"
        val stmt2 = "update next_id set sale_id = sale_id+1"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                con!!.prepareStatement(stmt2).use { ps2 ->
                    val resultSet = ps.executeQuery()
                    resultSet.next()
                    id = resultSet.getInt("sale_id")
                    ps2.execute()
                }
                ps.close()
                return id
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return id
    }


    fun loadList(
        list: ArrayList<Sale>,
        initDate: kotlin.String?,
        endDate: kotlin.String?,
        loadItems: Boolean
    ) {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val stmt =
            "select * from sale where cancelled = 0 and opening_date between ? and ?"
        val stmt2 = "select * from sale_item where cancelled=0 and sale_id = ?"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                con!!.prepareStatement(stmt2).use { ps2 ->
                    list.clear()
                    ps.setString(1, formatDateAppToDB(initDate!!))
                    ps.setString(2, formatDateAppToDB(endDate!!))
                    val resultSet = ps.executeQuery()
                    while (resultSet.next()) {
                        val sale = Sale()
                        this.auxLoad(sale, resultSet)
                        list.add(sale)
                    }
                    if (loadItems) {
                        for (sale in list) {
                            ps2.setInt(1, sale.id)
                            val resultSet2 = ps2.executeQuery()
                            while (resultSet2.next()) {
                                val item = SaleItem()
                                this.auxLoadItem(item, resultSet2)
                                item.saleId = sale.id
                                sale.items!!.add(item)
                            }
                        }
                    }
                }
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    private fun auxLoadItem(item: SaleItem, resultSet: ResultSet) {
        try {
            item.id = resultSet.getInt("id")
            item.saleId = resultSet.getInt("sale_id")
            if (item.user == null) {
                item.user = User()
            }
            item.time = formatTimeDBToApp(resultSet.getString("time"))
            item.type = resultSet.getInt("type")
            if (item.type != 1) {
                item.setProduct(Product())
            }
            if (item.type != 2) {
                item.setMaterial(Material())
            }
            if (item.type != 3) {
                item.setManufacture(Manufacture())
            }
            if (item.type != 4) {
                item.setService(Service())
            }
            if (item.type != 5) {
                item.setEquipment(Equipment())
            }
            item.unitPrice = resultSet.getInt("unit_price")
            item.amount = Amount(resultSet.getInt("amount")).divBy1000Amount
            item.value = resultSet.getInt("value")
            item.notes = resultSet.getString("notes")
            item.productionState = resultSet.getInt("production_state")
            item.productionBatch = resultSet.getInt("production_batch")
            item.isPendent = CustomBoolean(resultSet.getInt("pendent")).boolean
            item.payedAmount = Amount(resultSet.getInt("payed_amount")).divBy1000Amount
            item.payedValue = resultSet.getInt("payed_value")
            item.fatherItemId = resultSet.getInt("father_item_id")
            item.isCancelled = CustomBoolean(resultSet.getInt("cancelled")).boolean
            item.transferredFrom = resultSet.getInt("transfered_from")
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    private fun auxLoad(sale: Sale, resultSet: ResultSet) {
        try {
            sale.id = resultSet.getInt("id")
            sale.openingDate = formatDateDBToApp(resultSet.getString("opening_date"))
            sale.openingTime = formatTimeDBToApp(resultSet.getString("opening_time"))
            sale.shippingDate = formatDateDBToApp(resultSet.getString("shipping_date"))
            sale.shippingTime = formatTimeDBToApp(resultSet.getString("shipping_time"))
            sale.departureDate = formatDateDBToApp(resultSet.getString("departure_date"))
            sale.departureTime = formatTimeDBToApp(resultSet.getString("departure_time"))
            sale.deliverDate = formatDateDBToApp(resultSet.getString("deliver_date"))
            sale.deliverTime = formatTimeDBToApp(resultSet.getString("deliver_time"))
            sale.paymentDate = formatDateDBToApp(resultSet.getString("payment_date"))
            sale.paymentTime = formatTimeDBToApp(resultSet.getString("payment_time"))
            sale.type = resultSet.getInt("type")
            if (sale.type != 2) {
                sale.hallTableId = 0
            } else {
                sale.hallTableId = resultSet.getInt("hall_table_id")
            }
            if (sale.type != 3) {
                sale.cardId = 0
            } else {
                sale.cardId = resultSet.getInt("card_id")
            }
            sale.client = DBI07().getSingle("id",resultSet.getInt("client_id"))
            if (sale.client == null) {
                sale.client = Client()
            }
            sale.type2 = resultSet.getInt("type2")
            if (sale.subClient == null) {
                sale.subClient = SubClient()
            }
            sale.isDelivery = CustomBoolean(resultSet.getInt("delivery")).boolean
            sale.streetName = resultSet.getString("street_name")
            if (resultSet.getInt("neighborhood_id") == 0) {
                sale.neighborhood = Neighborhood()
            }
            sale.phone = resultSet.getString("phone")
            sale.representative = resultSet.getString("representative")
            if (sale.employee == null) {
                sale.employee = Employee()
            }
            if (sale.employee2 == null) {
                sale.employee2 = Employee()
            }
            if (sale.company == null) {
                sale.company = Company()
            }
            sale.notes = resultSet.getString("notes")
            sale.sumValue = resultSet.getInt("sum_value")
            sale.taxes = resultSet.getInt("taxes")
            sale.addition = resultSet.getInt("addition")
            sale.discount = resultSet.getInt("discount")
            sale.value = resultSet.getInt("value")
            sale.paymentMethod = resultSet.getString("payment_method")
            sale.changeFor = resultSet.getInt("change_for")
            sale.payedValue = resultSet.getInt("payed_value")
            sale.webSaleId = resultSet.getInt("web_sale_id")
            sale.pendentValue = resultSet.getInt("pendent_value")
            sale.divisor = resultSet.getInt("divisor")
            sale.isPayed = CustomBoolean(resultSet.getInt("payed")).boolean
            sale.deliverState = resultSet.getInt("deliver_state")
            sale.isPendent = CustomBoolean(resultSet.getInt("pendent")).boolean
            sale.isPendentItem = CustomBoolean(resultSet.getInt("pendent_item")).boolean
            sale.scheduleId = resultSet.getInt("schedule_id")
            sale.isCancelled = CustomBoolean(resultSet.getInt("cancelled")).boolean
            sale.fiscal = resultSet.getString("fiscal")
            sale.sessionId = resultSet.getInt("session_id")
            sale.sessionCounter = resultSet.getInt("session_counter")
            sale.isTaxesRule = CustomBoolean(resultSet.getInt("taxes_rule")).boolean
            sale.isDiscountRule = CustomBoolean(resultSet.getInt("discount_rule")).boolean
            sale.webSaleType = resultSet.getInt("web_sale_type")
            sale.webSaleCodes = resultSet.getString("web_sale_codes")
            sale.streetNumber = resultSet.getString("street_number")
            sale.complement = resultSet.getString("complement")
            sale.zipcode = resultSet.getString("zipcode")
            sale.coordinates = resultSet.getString("coordinates")
            sale.subDiscount = resultSet.getInt("sub_discount")
            sale.isIfoodDelivery = resultSet.getBoolean("is_ifood_delivery")
            sale.ifoodDeliveryTaxes = resultSet.getInt("ifood_delivery_tax")
            sale.discountReason = resultSet.getString("discount_reason")
            sale.userIdDiscount = resultSet.getInt("user_id_discount")
            sale.isIfoodSchedule = resultSet.getBoolean("is_ifood_schedule")
            sale.isIfoodIndoor = resultSet.getBoolean("is_ifood_indoor")
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }

    fun add(sale: Sale, type: Int): Boolean {
        Class.forName("com.mysql.jdbc.Driver")
        con = DriverManager.getConnection(banco,"root","1234")
        val stmt = "insert into sale (id, opening_date, opening_time, shipping_date, shipping_time, departure_date," +
                " departure_time, deliver_date, deliver_time, payment_date, payment_time, type, app_user_id, hall_table_id, " +
                "card_id, client_id, type2, sub_client_id, delivery, street_name, neighborhood_id, phone, employee_id," +
                " employee2_id, representative, company_id, notes, sum_value, taxes, addition, discount, value, " +
                "payment_method, change_for, payed_value, web_sale_id, pendent_value, divisor, payed, deliver_state, " +
                "pendent, pendent_item, schedule_id, cancelled, fiscal, session_id, session_counter, taxes_rule, " +
                "discount_rule, web_sale_type, web_sale_codes, street_number, complement, zipcode, coordinates, sub_discount, " +
                "is_ifood_delivery, ifood_delivery_tax, discount_reason, user_id_discount, is_ifood_schedule, is_ifood_indoor) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        try {
            con!!.prepareStatement(stmt).use { ps ->
                ps.setInt(1, sale.id)
                ps.setString(2, formatDateAppToDB(sale.openingDate!!))
                ps.setString(3, sale.openingTime)
                ps.setString(4, formatDateAppToDB(sale.shippingDate!!))
                ps.setString(5, sale.shippingTime)
                ps.setString(6, formatDateAppToDB(sale.departureDate!!))
                ps.setString(7, sale.departureTime)
                ps.setString(8, formatDateAppToDB(sale.deliverDate!!))
                ps.setString(9, sale.deliverTime)
                ps.setString(10, formatDateAppToDB(sale.paymentDate!!))
                ps.setString(11, sale.paymentTime)
                ps.setInt(12, sale.type)
                ps.setInt(13, sale.user!!.id)
                ps.setInt(14, sale.hallTableId)
                ps.setInt(15, sale.cardId)
                ps.setInt(16, sale.client!!.id)
                ps.setInt(17, sale.type2)
                ps.setInt(18, sale.subClient!!.id)
                ps.setInt(19, CustomBoolean(sale.isDelivery).int)
                ps.setString(20, sale.streetName)
                ps.setInt(21, sale.neighborhood!!.id)
                ps.setString(22, sale.phone)
                ps.setInt(23, sale.employee!!.id)
                ps.setInt(24, sale.employee2!!.id)
                ps.setString(25, sale.representative)
                ps.setInt(26, sale.company!!.id)
                ps.setString(27, sale.notes)
                ps.setInt(28, sale.sumValue)
                ps.setInt(29, sale.taxes)
                ps.setInt(30, sale.addition)
                ps.setInt(31, sale.discount)
                ps.setInt(32, sale.value)
                ps.setString(33, sale.paymentMethod)
                ps.setInt(34, sale.changeFor)
                ps.setInt(35, sale.payedValue)
                ps.setInt(36, sale.webSaleId)
                ps.setInt(37, sale.pendentValue)
                ps.setInt(38, sale.divisor)
                ps.setInt(39, CustomBoolean(sale.isPayed).int)
                ps.setInt(40, sale.deliverState)
                ps.setInt(41, CustomBoolean(sale.isPendent).int)
                ps.setInt(42, CustomBoolean(sale.isPendentItem).int)
                ps.setInt(43, sale.scheduleId)
                ps.setInt(44, CustomBoolean(sale.isCancelled).int)
                ps.setString(45, sale.fiscal)
                ps.setInt(46, sale.sessionId)
                ps.setInt(47, sale.sessionCounter)
                ps.setInt(48, CustomBoolean(sale.isTaxesRule).int)
                ps.setInt(49, CustomBoolean(sale.isDiscountRule).int)
                ps.setInt(50, sale.webSaleType)
                ps.setString(51, sale.webSaleCodes)
                ps.setString(52, sale.streetNumber)
                ps.setString(53, sale.complement)
                ps.setString(54, sale.zipcode)
                ps.setString(55, sale.coordinates)
                ps.setInt(56, sale.subDiscount)
                ps.setBoolean(57, sale.isIfoodDelivery)
                ps.setInt(58, sale.ifoodDeliveryTaxes)
                ps.setString(59, sale.discountReason)
                ps.setInt(60, sale.userIdDiscount)
                ps.setBoolean(61, sale.isIfoodSchedule)
                ps.setBoolean(62, sale.isIfoodIndoor)
                ps.execute()
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
            return false
        }
        return true
    }

    fun formatDateAppToDB(date: kotlin.String): kotlin.String? {
        if (date == "") {
            return null
        }
        val fields = date.split("/").toTypedArray()
        return fields[2] + fields[1] + fields[0]
    }

    fun formatDateDBToApp(date: String?): String? {
        if (date == null || date == "") {
            return ""
        }
        if (date.contains("-")) {
            val fields = date.split("-").toTypedArray()
            return fields[2] + "/" + fields[1] + "/" + fields[0]
        }
        return date.substring(6) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4)
    }

    fun formatTimeDBToApp(time: String): String? {
        if (time == "") {
            return "12:00"
        }
        val fields = time.split(":").toTypedArray()
        return fields[0] + ":" + fields[1]
    }
}