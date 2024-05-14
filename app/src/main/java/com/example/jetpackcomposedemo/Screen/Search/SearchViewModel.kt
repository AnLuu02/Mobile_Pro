package com.example.jetpackcomposedemo.Screen.Search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Discount(
    val title:String? = null,
    val percent:Int? = null
)
data class BookRoom(
    val timeCheckin: String = "Bất kì",
    val timeCheckOut:String = "Bất kì",
    val totalTime:Int = 1,
    var discount:Discount? = null
)


data class  FilterRoom(
    val minPriceRoom:Int? = null,
    val maxPriceRoom:Int? = null,
    val rateScore:String? = null,
    val cleanScore:String? = null,
    val typeRoom:String? = null,
    val utilitiesRoom:List<String>? = null,
)

data class SortMethod(
    val sortMethod:String? = null
)
class SearchViewModel:ViewModel() {

    // Lưu trữ các trạng thái đã chọn của lịch
    private val _hourSelectedCalendar = mutableStateOf(BookRoom())
    private val _overnightSelectedCalendar = mutableStateOf(BookRoom())
    private val _daySelectedCalendar  = mutableStateOf(BookRoom())

    //trang thai filter and sorted
    private var _filterRoom  = mutableStateOf(FilterRoom())
    private var _sortRoom  = mutableStateOf(SortMethod())

    fun getSelectedCalendar(typeBooking:String): MutableState<BookRoom> {
        return when(typeBooking){
            "hourly"->_hourSelectedCalendar
            "overnight"->_overnightSelectedCalendar
            else-> _daySelectedCalendar

        }
    }

    fun getTimeCheckin(typeBooking: String):String{
        return when(typeBooking){
            "hourly"->_hourSelectedCalendar.value.timeCheckin
            "overnight"->_overnightSelectedCalendar.value.timeCheckin
            else -> _daySelectedCalendar.value.timeCheckin
        }
    }

    fun getFilterRoom(): MutableState<FilterRoom>{
        return _filterRoom
    }
    fun getSortMethod(): MutableState<SortMethod>{
        return _sortRoom
    }

    fun setSelectedCalendar(typeBooking:String,timeCheckin: BookRoom) {
        when(typeBooking){
            "hourly"->_hourSelectedCalendar.value = timeCheckin
            "overnight"->_overnightSelectedCalendar.value = timeCheckin
            else -> _daySelectedCalendar.value = timeCheckin
        }
    }

    fun setDiscount(typeBooking: String, discount: Discount?){
        when(typeBooking){
            "hourly"->_hourSelectedCalendar.value.discount = discount
            "overnight"->_overnightSelectedCalendar.value.discount = discount
            else -> _daySelectedCalendar.value.discount = discount
        }
    }


    fun setFilterRoom(filterRoom: FilterRoom) {
        _filterRoom.value = filterRoom
    }
    fun setSortMethod(sortMethod: SortMethod) {
        _sortRoom.value = sortMethod
    }
    fun getOnlyDayBooking(typeBooking: String): BookRoom {
        val regexDay = "\\b\\d{2}:\\d{2}, (\\d{2})/\\d{2}/\\d{4}\\b".toRegex()
        return when (typeBooking) {
            "hourly" ->
                BookRoom(
                    timeCheckin = regexDay.find(_hourSelectedCalendar.value.timeCheckin)?.groupValues?.get(1) ?: "Bất kì",
                    timeCheckOut = regexDay.find(_hourSelectedCalendar.value.timeCheckOut)?.groupValues?.get(1) ?: "Bất kì",
                    totalTime = _hourSelectedCalendar.value.totalTime
                )

            "overnight"->

                BookRoom(
                    timeCheckin = regexDay.find(_overnightSelectedCalendar.value.timeCheckin)?.groupValues?.get(1) ?: "Bất kì",
                    timeCheckOut = regexDay.find(_overnightSelectedCalendar.value.timeCheckOut)?.groupValues?.get(1) ?: "Bất kì",
                    totalTime = _overnightSelectedCalendar.value.totalTime
                )

            else -> {
                BookRoom(
                    timeCheckin = regexDay.find(_daySelectedCalendar.value.timeCheckin)?.groupValues?.get(1) ?: "Bất kì",
                    timeCheckOut = regexDay.find(_daySelectedCalendar.value.timeCheckOut)?.groupValues?.get(1) ?: "Bất kì",
                    totalTime = _daySelectedCalendar.value.totalTime
                )
            }
        }
    }
    fun getDateNotYear(typeBooking:String): BookRoom {
        val regex = "/\\d{4}".toRegex()
        return when (typeBooking) {
            "hourly" ->
                BookRoom(
                    timeCheckin = if(_hourSelectedCalendar.value.timeCheckin != "Bất kì")
                        _hourSelectedCalendar.value.timeCheckin.replace(regex, "")
                    else "Bất kì",
                    timeCheckOut = if(_hourSelectedCalendar.value.timeCheckOut != "Bất kì")
                        _hourSelectedCalendar.value.timeCheckOut.replace(regex, "")
                    else "Bất kì",
                    totalTime = _hourSelectedCalendar.value.totalTime
                )
            "overnight"->

                BookRoom(
                    timeCheckin = if(_overnightSelectedCalendar.value.timeCheckin != "Bất kì")
                        _overnightSelectedCalendar.value.timeCheckin.replace(regex, "")
                    else "Bất kì",
                    timeCheckOut = if(_overnightSelectedCalendar.value.timeCheckOut != "Bất kì")
                        _overnightSelectedCalendar.value.timeCheckOut.replace(regex, "")
                    else "Bất kì",
                    totalTime = _overnightSelectedCalendar.value.totalTime
                )


            else -> {

                BookRoom(
                    timeCheckin = if(_daySelectedCalendar.value.timeCheckin != "Bất kì")
                        _daySelectedCalendar.value.timeCheckin.replace(regex, "")
                    else "Bất kì",
                    timeCheckOut = if(_daySelectedCalendar.value.timeCheckOut != "Bất kì")
                        _daySelectedCalendar.value.timeCheckOut.replace(regex, "")
                    else "Bất kì",
                    totalTime = _daySelectedCalendar.value.totalTime
                )
            }
        }
    }
    fun getDayAndMonth(typeBooking: String): BookRoom {
        val regex = Regex("\\d{2}/\\d{2}")
        return when (typeBooking) {
            "hourly" ->
                BookRoom(
                    timeCheckin = _hourSelectedCalendar.value.timeCheckin.let { regex.find(it)?.value }
                        ?: "Bất kì",
                    timeCheckOut = _hourSelectedCalendar.value.timeCheckOut.let { regex.find(it)?.value }
                        ?: "Bất kì",
                    totalTime = _hourSelectedCalendar.value.totalTime
                )
            "overnight"->
                BookRoom(
                    timeCheckin = _overnightSelectedCalendar.value.timeCheckin.let { regex.find(it)?.value } ?: "Bất kì",
                    timeCheckOut = _overnightSelectedCalendar.value.timeCheckOut.let { regex.find(it)?.value } ?: "Bất kì",
                    totalTime = _overnightSelectedCalendar.value.totalTime
                )
            else ->
                BookRoom(
                    timeCheckin = _daySelectedCalendar.value.timeCheckin.let { regex.find(it)?.value } ?: "Bất kì",
                    timeCheckOut = _daySelectedCalendar.value.timeCheckOut.let { regex.find(it)?.value } ?: "Bất kì",
                    totalTime = _daySelectedCalendar.value.totalTime
                )
        }
    }
    fun getOnlyHourBooking(typeBooking:String): BookRoom {
        return when(typeBooking){
            "hourly"->
                BookRoom(
                    timeCheckin = if(_hourSelectedCalendar.value.timeCheckin!="Bất kì")
                        _hourSelectedCalendar.value.timeCheckin.split(":")[0] else "Bất kì",
                    timeCheckOut = if(_hourSelectedCalendar.value.timeCheckOut!="Bất kì")
                        _hourSelectedCalendar.value.timeCheckOut.split(":")[0] else "Bất kì",
                    totalTime = _hourSelectedCalendar.value.totalTime
                )
            "overnight"->
                BookRoom(
                    timeCheckin = if(_overnightSelectedCalendar.value.timeCheckin!="Bất kì")
                        _overnightSelectedCalendar.value.timeCheckin.split(":")[0] else "Bất kì",
                    timeCheckOut = if(_overnightSelectedCalendar.value.timeCheckOut!="Bất kì")
                        _overnightSelectedCalendar.value.timeCheckOut.split(":")[0] else "Bất kì",
                    totalTime = _overnightSelectedCalendar.value.totalTime
                )

            else ->

                BookRoom(
                    timeCheckin = if(_daySelectedCalendar.value.timeCheckin!="Bất kì")
                        _daySelectedCalendar.value.timeCheckin.split(":")[0] else "Bất kì",
                    timeCheckOut = if(_daySelectedCalendar.value.timeCheckOut!="Bất kì")
                        _daySelectedCalendar.value.timeCheckOut.split(":")[0] else "Bất kì",
                    totalTime = _daySelectedCalendar.value.totalTime
                )
        }
    }
    fun getDateSearch(typeBooking: String): BookRoom {
        return when(typeBooking){
            "hourly"->getDateNotYear(typeBooking)
            "overnight"->getDayAndMonth(typeBooking)
            else->{
                getDayAndMonth(typeBooking)
            }
        }
    }
    fun getTotalDate(typeBooking: String): Int {
        return when(typeBooking){
            "hourly"-> _hourSelectedCalendar.value.totalTime
            "overnight"-> _overnightSelectedCalendar.value.totalTime
            else -> _daySelectedCalendar.value.totalTime
        }
    }
}
