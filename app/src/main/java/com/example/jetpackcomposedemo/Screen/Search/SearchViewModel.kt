package com.example.jetpackcomposedemo.Screen.Search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Bookroom(
    val timeCheckin: String = "Bất kì",
    val timeCheckOut:String = "Bất kì",
    val totalTime:Int = 1,
)
class SearchViewModel:ViewModel() {

    // Lưu trữ các trạng thái đã chọn của lịch
    private val _hourSelectedCalendar = mutableStateOf(Bookroom())
    private val _overnightSelectedCalendar = mutableStateOf(Bookroom())
    private val _daySelectedCalendar  = mutableStateOf(Bookroom())

    fun getSelectedCalendar(typeBooking:String): MutableState<Bookroom> {
        return when(typeBooking){
            "hourly"->_hourSelectedCalendar
            "overnight"->_overnightSelectedCalendar
            else -> _daySelectedCalendar
        }
    }

    fun setSelectedCalendar(typeBooking:String,timeCheckin: Bookroom) {
        when(typeBooking){
            "hourly"->_hourSelectedCalendar.value = timeCheckin
            "overnight"->_overnightSelectedCalendar.value = timeCheckin
            else -> _daySelectedCalendar.value = timeCheckin
        }
    }
    fun getOnlyDayBooking(typeBooking: String): Bookroom {
        val regexDay = "\\b\\d{2}:\\d{2}, (\\d{2})/\\d{2}/\\d{4}\\b".toRegex()
        return when (typeBooking) {
            "hourly" ->
                Bookroom(
                    timeCheckin = regexDay.find(_hourSelectedCalendar.value.timeCheckin)?.groupValues?.get(1) ?: "Bất kì",
                    timeCheckOut = regexDay.find(_hourSelectedCalendar.value.timeCheckOut)?.groupValues?.get(1) ?: "Bất kì",
                    totalTime = _hourSelectedCalendar.value.totalTime
                )

            "overnight"->

                Bookroom(
                    timeCheckin = regexDay.find(_overnightSelectedCalendar.value.timeCheckin)?.groupValues?.get(1) ?: "Bất kì",
                    timeCheckOut = regexDay.find(_overnightSelectedCalendar.value.timeCheckOut)?.groupValues?.get(1) ?: "Bất kì",
                    totalTime = _overnightSelectedCalendar.value.totalTime
                )

            else -> {
                Bookroom(
                    timeCheckin = regexDay.find(_daySelectedCalendar.value.timeCheckin)?.groupValues?.get(1) ?: "Bất kì",
                    timeCheckOut = regexDay.find(_daySelectedCalendar.value.timeCheckOut)?.groupValues?.get(1) ?: "Bất kì",
                    totalTime = _daySelectedCalendar.value.totalTime
                )
            }
        }
    }
    fun getDateNotYear(typeBooking:String): Bookroom {
        val regex = "/\\d{4}".toRegex()
        return when (typeBooking) {
            "hourly" ->
                Bookroom(
                    timeCheckin = if(_hourSelectedCalendar.value.timeCheckin != "Bất kì")
                        _hourSelectedCalendar.value.timeCheckin.replace(regex, "")
                    else "Bất kì",
                    timeCheckOut = if(_hourSelectedCalendar.value.timeCheckOut != "Bất kì")
                        _hourSelectedCalendar.value.timeCheckOut.replace(regex, "")
                    else "Bất kì",
                    totalTime = _hourSelectedCalendar.value.totalTime
                )
            "overnight"->

                Bookroom(
                    timeCheckin = if(_overnightSelectedCalendar.value.timeCheckin != "Bất kì")
                        _overnightSelectedCalendar.value.timeCheckin.replace(regex, "")
                    else "Bất kì",
                    timeCheckOut = if(_overnightSelectedCalendar.value.timeCheckOut != "Bất kì")
                        _overnightSelectedCalendar.value.timeCheckOut.replace(regex, "")
                    else "Bất kì",
                    totalTime = _overnightSelectedCalendar.value.totalTime
                )


            else -> {

                Bookroom(
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
    fun getDayAndMonth(typeBooking: String): Bookroom {
        val regex = Regex("\\d{2}/\\d{2}")
        return when (typeBooking) {
            "hourly" ->
                Bookroom(
                    timeCheckin = _hourSelectedCalendar.value.timeCheckin.let { regex.find(it)?.value }
                        ?: "Bất kì",
                    timeCheckOut = _hourSelectedCalendar.value.timeCheckOut.let { regex.find(it)?.value }
                        ?: "Bất kì",
                    totalTime = _hourSelectedCalendar.value.totalTime
                )
            "overnight"->
                Bookroom(
                    timeCheckin = _overnightSelectedCalendar.value.timeCheckin.let { regex.find(it)?.value } ?: "Bất kì",
                    timeCheckOut = _overnightSelectedCalendar.value.timeCheckOut.let { regex.find(it)?.value } ?: "Bất kì",
                    totalTime = _overnightSelectedCalendar.value.totalTime
                )
            else ->
                Bookroom(
                    timeCheckin = _daySelectedCalendar.value.timeCheckin.let { regex.find(it)?.value } ?: "Bất kì",
                    timeCheckOut = _daySelectedCalendar.value.timeCheckOut.let { regex.find(it)?.value } ?: "Bất kì",
                    totalTime = _daySelectedCalendar.value.totalTime
                )
        }
    }
    fun getOnlyHourBooking(typeBooking:String): Bookroom {
        return when(typeBooking){
            "hourly"->
                Bookroom(
                    timeCheckin = if(_hourSelectedCalendar.value.timeCheckin!="Bất kì")
                        _hourSelectedCalendar.value.timeCheckin.split(":")[0] else "Bất kì",
                    timeCheckOut = if(_hourSelectedCalendar.value.timeCheckOut!="Bất kì")
                        _hourSelectedCalendar.value.timeCheckOut.split(":")[0] else "Bất kì",
                    totalTime = _hourSelectedCalendar.value.totalTime
                )
            "overnight"->
                Bookroom(
                    timeCheckin = if(_overnightSelectedCalendar.value.timeCheckin!="Bất kì")
                        _overnightSelectedCalendar.value.timeCheckin.split(":")[0] else "Bất kì",
                    timeCheckOut = if(_overnightSelectedCalendar.value.timeCheckOut!="Bất kì")
                        _overnightSelectedCalendar.value.timeCheckOut.split(":")[0] else "Bất kì",
                    totalTime = _overnightSelectedCalendar.value.totalTime
                )

            else ->

                Bookroom(
                    timeCheckin = if(_daySelectedCalendar.value.timeCheckin!="Bất kì")
                        _daySelectedCalendar.value.timeCheckin.split(":")[0] else "Bất kì",
                    timeCheckOut = if(_daySelectedCalendar.value.timeCheckOut!="Bất kì")
                        _daySelectedCalendar.value.timeCheckOut.split(":")[0] else "Bất kì",
                    totalTime = _daySelectedCalendar.value.totalTime
                )
        }
    }
    fun getDateSearch(typeBooking: String): Bookroom {
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


//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//
//data class Bookroom(
//    val timeCheckin: String = "",
//    val timeCheckOut:String = "",
//    val totalTime:Int = 1,
//)
//class SearchViewModel:ViewModel() {
//    private val _timeBookroom = mutableStateOf<Bookroom?>(null)
//    val getTimeBookroom: MutableState<Bookroom?> = _timeBookroom
//
//    fun setTimeBookroom(timeCheckin: Bookroom) {
//        _timeBookroom.value = timeCheckin
//    }
//
//    fun getDayAndMonth(): Bookroom? {
//        val regex = "/\\d{4}".toRegex()
//        return _timeBookroom.value?.timeCheckin?.let {
//            _timeBookroom.value?.timeCheckOut?.let { it1 ->
//                Bookroom(
//                    timeCheckin = it.replace(regex, ""),
//                    timeCheckOut = it1.replace(regex, ""),
//                    totalTime = _timeBookroom.value!!.totalTime
//                )
//            }
//        }
//    }
//    fun getOnlyHourCheckin(): Int {
//        return _timeBookroom.value?.timeCheckin?.split(":")?.get(0)?.toInt() ?: -1
//    }
//
//    fun getOnlyHourCheckout(): Int {
//        return _timeBookroom.value?.timeCheckOut?.split(":")?.get(0)?.toInt() ?: -1
//    }
//
//    fun getTotalDate(): Int {
//        return _timeBookroom.value?.totalTime ?: -1
//    }
//}
//
