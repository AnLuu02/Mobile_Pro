package com.example.jetpackcomposedemo.Screen.Search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Bookroom(
    val timeCheckin: String = "",
    val timeCheckOut:String = "",
    val totalTime:Int = 1,
)
class SearchViewModel:ViewModel() {

    // Lưu trữ các trạng thái đã chọn của lịch
    private val _hourSelectedCalendar = mutableStateOf<Bookroom?>(null)
    private val _overnightSelectedCalendar = mutableStateOf<Bookroom?>(null)
    private val _daySelectedCalendar  = mutableStateOf<Bookroom?>(null)

//
//    val getHourSelectedCalendar: MutableState<Bookroom?> = _hourSelectedCalendar
//    val getOvernightSelectedCalendar: MutableState<Bookroom?> = _overnightSelectedCalendar
//    val getDaySelectedCalendar: MutableState<Bookroom?> = _daySelectedCalendar
//

    fun getSelectedCalendar(typeBooking:String): MutableState<Bookroom?> {
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
    fun getOnlyDayBooking(typeBooking: String): Bookroom? {
        val regexDay = "\\b\\d{2}:\\d{2}, (\\d{2})/\\d{2}/\\d{4}\\b".toRegex()
        val matchDayResult = regexDay.find(_hourSelectedCalendar.value?.timeCheckin ?: "")!!.groupValues[1]
        return when (typeBooking) {
            "hourly" ->
                _hourSelectedCalendar.value?.timeCheckin?.let {
                    _hourSelectedCalendar.value?.timeCheckOut?.let { it1 ->
                        Bookroom(
                            timeCheckin = regexDay.find(it ?: "")!!.groupValues[1],
                            timeCheckOut = regexDay.find(it1 ?: "")!!.groupValues[1],
                            totalTime = _hourSelectedCalendar.value!!.totalTime
                        )
                    }
                }
            "overnight"->
                _overnightSelectedCalendar.value?.timeCheckin?.let {
                    _overnightSelectedCalendar.value?.timeCheckOut?.let { it1 ->
                        Bookroom(
                            timeCheckin = regexDay.find(it ?: "")!!.groupValues[1],
                            timeCheckOut = regexDay.find(it1 ?: "")!!.groupValues[1],
                            totalTime = _overnightSelectedCalendar.value!!.totalTime
                        )
                    }
                }
            else -> {
                _daySelectedCalendar.value?.timeCheckin?.let {
                    _daySelectedCalendar.value?.timeCheckOut?.let { it1 ->
                        Bookroom(
                            timeCheckin = regexDay.find(it ?: "")!!.groupValues[1],
                            timeCheckOut = regexDay.find(it1 ?: "")!!.groupValues[1],
                            totalTime = _daySelectedCalendar.value!!.totalTime
                        )
                    }
                }
            }
        }
    }

    fun getDateNotYear(typeBooking:String): Bookroom? {
        val regex = "/\\d{4}".toRegex()
        return when (typeBooking) {
            "hourly" ->
                _hourSelectedCalendar.value?.timeCheckin?.let {
                    _hourSelectedCalendar.value?.timeCheckOut?.let { it1 ->
                        Bookroom(
                            timeCheckin = it.replace(regex, ""),
                            timeCheckOut = it1.replace(regex, ""),
                            totalTime = _hourSelectedCalendar.value!!.totalTime
                        )
                    }
                }
            "overnight"->
                _overnightSelectedCalendar.value?.timeCheckin?.let {
                    _overnightSelectedCalendar.value?.timeCheckOut?.let { it1 ->
                        Bookroom(
                            timeCheckin = it.replace(regex, ""),
                            timeCheckOut = it1.replace(regex, ""),
                            totalTime = _overnightSelectedCalendar.value!!.totalTime
                        )
                    }
                }
            else -> {
                _daySelectedCalendar.value?.timeCheckin?.let {
                    _daySelectedCalendar.value?.timeCheckOut?.let { it1 ->
                        Bookroom(
                            timeCheckin = it.replace(regex, ""),
                            timeCheckOut = it1.replace(regex, ""),
                            totalTime = _daySelectedCalendar.value!!.totalTime
                        )
                    }
                }
            }
        }
    }
    fun getDayAndMonth(typeBooking:String): Bookroom? {
        val regex = Regex("\\d{2}/\\d{2}")
        return when (typeBooking) {
            "hourly" ->
                Bookroom(
                    timeCheckin = regex.find(_hourSelectedCalendar.value?.timeCheckin.toString())?.value ?: "",
                    timeCheckOut = regex.find(_hourSelectedCalendar.value?.timeCheckOut.toString())?.value ?: "",
                    totalTime = _hourSelectedCalendar.value!!.totalTime
                )
            "overnight"->
                Bookroom(
                    timeCheckin = regex.find(_overnightSelectedCalendar.value?.timeCheckin.toString())?.value ?: "",
                    timeCheckOut = regex.find(_overnightSelectedCalendar.value?.timeCheckOut.toString())?.value ?: "",
                    totalTime = _overnightSelectedCalendar.value!!.totalTime
                )

            else -> {
                Bookroom(
                    timeCheckin = regex.find(_daySelectedCalendar.value?.timeCheckin.toString())?.value ?: "",
                    timeCheckOut = regex.find(_daySelectedCalendar.value?.timeCheckOut.toString())?.value ?: "",
                    totalTime = _daySelectedCalendar.value!!.totalTime
                )
            }
        }
    }
    fun getOnlyHourBooking(typeBooking:String): Bookroom? {
        return when(typeBooking){
            "hourly"->
                _hourSelectedCalendar.value?.timeCheckin?.split(":")?.get(0)?.let {
                    _hourSelectedCalendar.value?.timeCheckOut?.split(":")?.get(0)?.let { it1 ->
                        Bookroom(
                            timeCheckin = it,
                            timeCheckOut = it1,
                            totalTime = _hourSelectedCalendar.value!!.totalTime
                        )
                    }
                }
            "overnight"->
                _overnightSelectedCalendar.value?.timeCheckin?.split(":")?.get(0)?.let {
                    _overnightSelectedCalendar.value?.timeCheckOut?.split(":")?.get(0)?.let { it1 ->
                        Bookroom(
                            timeCheckin = it,
                            timeCheckOut = it1,
                            totalTime = _overnightSelectedCalendar.value!!.totalTime
                        )
                    }
                }
            else ->
                _daySelectedCalendar.value?.timeCheckin?.split(":")?.get(0)?.let {
                    _daySelectedCalendar.value?.timeCheckOut?.split(":")?.get(0)?.let { it1 ->
                        Bookroom(
                            timeCheckin = it,
                            timeCheckOut = it1,
                            totalTime = _daySelectedCalendar.value!!.totalTime
                        )
                    }
                }
        }
    }
    fun getDateSearch(typeBooking: String): Bookroom? {
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
            "hourly"-> _hourSelectedCalendar.value?.totalTime ?: 1
            "overnight"-> _overnightSelectedCalendar.value?.totalTime ?: 1
            else -> _daySelectedCalendar.value?.totalTime ?: 1
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
