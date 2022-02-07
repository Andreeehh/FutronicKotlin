package auxiliary

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CustomDate : Comparable<CustomDate?> {
    var calendar: Calendar?
        private set

    constructor() {
        calendar = GregorianCalendar()
    }

    constructor(dateString: String?) {
        calendar = GregorianCalendar()
        this.setDate(dateString)
    }

    constructor(dateString: String?, timeString: String) {
        calendar = GregorianCalendar()
        this.setDate(dateString, timeString)
    }

    fun setDate(dateString: String?) {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        if (calendar == null) {
            calendar = GregorianCalendar()
        }
        if (dateString != null) {
            try {
                calendar!!.time = simpleDateFormat.parse(getCheckedDate(dateString) + " 00:00")
            } catch (e: ParseException) {
                calendar = null
            }
        } else {
            calendar = null
        }
    }

    fun setDate(dateString: String?, timeString: String) {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        if (calendar == null) {
            calendar = GregorianCalendar()
        }
        if (dateString != null) {
            try {
                calendar!!.time = simpleDateFormat.parse(getCheckedDate(dateString) + " " + timeString)
            } catch (e: ParseException) {
                calendar = null
            }
        } else {
            calendar = null
        }
    }

    fun getCheckedDate(date: String): String? {
        if (date.trim { it <= ' ' } == "" || date.trim { it <= ' ' } == "//") {
            return null
        }
        val fields = date.split("/").toTypedArray()
        if (fields[0].compareTo(getLastDayOfMonth(fields[1], fields[2])) > 0) {
            fields[0] = getLastDayOfMonth(fields[1], fields[2])
        }
        if (fields[1].compareTo("12") > 0) {
            fields[1] = "12"
        }
        return fields[0] + "/" + fields[1] + "/" + fields[2]
    }

    fun getLastDayOfMonth(month: String, year: String): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val cal: Calendar = GregorianCalendar()
        try {
            cal.time = simpleDateFormat.parse("01/$month/$year")
        } catch (e: ParseException) {
            calendar = null
        }
        cal.add(Calendar.MONTH, 1)
        cal[Calendar.DATE] = 1
        cal.add(Calendar.DATE, -1)
        val last = cal[Calendar.DATE]
        return if (last < 10) {
            "0$last"
        } else Integer.toString(last)
    }

    fun getMonthFromStringDBFormat(stringDate: String): String {
        val dateArray = stringDate.split("/").toTypedArray()
        return dateArray[1]
    }

    fun getDayFromStringDBFormat(stringDate: String): String {
        val dateArray = stringDate.split("/").toTypedArray()
        return dateArray[0]
    }

    val timeString: String
        get() {
            val simpleDateFormat = SimpleDateFormat("HH:mm")
            return if (calendar != null) {
                simpleDateFormat.format(calendar!!.time)
            } else ""
        }

    override fun toString(): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return if (calendar != null) {
            simpleDateFormat.format(calendar!!.time)
        } else ""
    }

    val dateTime: String
        get() = this.toString() + " " + timeString
    val dayMonthString: String
        get() {
            val simpleDateFormat = SimpleDateFormat("dd/MM")
            return if (calendar != null) {
                simpleDateFormat.format(calendar!!.time)
            } else ""
        }
    val monthYearString: String
        get() {
            val simpleDateFormat = SimpleDateFormat("MM/yyyy")
            return if (calendar != null) {
                simpleDateFormat.format(calendar!!.time)
            } else ""
        }

    fun getExtendedMonthYearString(separator: String): String {
        val simpleDateFormat = SimpleDateFormat("MM/yyyy")
        if (calendar == null) {
            return ""
        }
        val fields = simpleDateFormat.format(calendar!!.time).split("/").toTypedArray()
        if (fields[0] == "01") {
            return "Janeiro" + separator + fields[1]
        }
        if (fields[0] == "02") {
            return "Fevereiro" + separator + fields[1]
        }
        if (fields[0] == "03") {
            return "Mar\ufffdo" + separator + fields[1]
        }
        if (fields[0] == "04") {
            return "Abril" + separator + fields[1]
        }
        if (fields[0] == "05") {
            return "Maio" + separator + fields[1]
        }
        if (fields[0] == "06") {
            return "Junho" + separator + fields[1]
        }
        if (fields[0] == "07") {
            return "Julho" + separator + fields[1]
        }
        if (fields[0] == "08") {
            return "Agosto" + separator + fields[1]
        }
        if (fields[0] == "09") {
            return "Setembro" + separator + fields[1]
        }
        if (fields[0] == "10") {
            return "Outubro" + separator + fields[1]
        }
        return if (fields[0] == "11") {
            "Novembro" + separator + fields[1]
        } else "Dezembro" + separator + fields[1]
    }

    fun getYearMonthString(separator: String): String {
        val temp = monthYearString
        return temp.split("/").toTypedArray()[1] + separator + temp.split("/").toTypedArray()[0]
    }

    val yearString: String
        get() {
            val simpleDateFormat = SimpleDateFormat("yyyy")
            return if (calendar != null) {
                simpleDateFormat.format(calendar!!.time)
            } else ""
        }

    /*operator fun compareTo(date2: CustomDate): Int {
        if (this.toString() == "" && date2.toString() != "") {
            return 1
        }
        if (this.toString() != "" && date2.toString() == "") {
            return -1
        }
        if (this.toString() == "" && date2.toString() == "") {
            return 0
        }
        calendar!![Calendar.SECOND] = 0
        calendar!![Calendar.MILLISECOND] = 0
        date2.calendar!![Calendar.SECOND] = 0
        date2.calendar!![Calendar.MILLISECOND] = 0
        return calendar!!.compareTo(date2.calendar)
    }*/

    override fun compareTo(date2: CustomDate?): Int {
        if (this.toString() == "" && date2.toString() != "") {
            return 1
        }
        if (this.toString() != "" && date2.toString() == "") {
            return -1
        }
        if (this.toString() == "" && date2.toString() == "") {
            return 0
        }
        calendar!![Calendar.SECOND] = 0
        calendar!![Calendar.MILLISECOND] = 0
        date2?.calendar!![Calendar.SECOND] = 0
        date2.calendar!![Calendar.MILLISECOND] = 0
        return calendar!!.compareTo(date2.calendar)
    }
}
