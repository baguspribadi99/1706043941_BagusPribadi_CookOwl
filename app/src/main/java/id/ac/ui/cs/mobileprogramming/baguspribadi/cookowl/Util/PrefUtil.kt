package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Util

import android.content.Context
import android.preference.PreferenceManager
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.TimerFragment

class PrefUtil {
    companion object{
        private var timer_length:Int = 0

        fun setTimerLength(length: Int, context: Context){
            timer_length = length
        }

        fun getTimerLength(context : Context): Int{
            return timer_length
        }

        private const val PREVIOUS_TIMER_LENGTH_ID = "id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.prev_timer_length"
        private const val REMAINING_TIMER_LENGTH_ID = "id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.remaining_timer_length"
        private const val TIMER_STATE_ID = "id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.timer_state"
        private const val ALARM_SET_TIME_ID = "id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.alarm_set_time_id"

        fun getPreviousTimerLength(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_ID, 0)
        }

        fun setPreviousTimerLength(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_ID, seconds)
            editor.apply()
        }

        fun getRemainingTimerLength(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(REMAINING_TIMER_LENGTH_ID, 0)
        }

        fun setRemainingTimerLength(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(REMAINING_TIMER_LENGTH_ID, seconds)
            editor.apply()
        }

        fun getTimerState(context: Context): Boolean{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getBoolean(TIMER_STATE_ID, false)
        }

        fun setTimerState(status: Boolean, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putBoolean(TIMER_STATE_ID, status)
            editor.apply()
        }

        fun getAlarmSetTime(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(ALARM_SET_TIME_ID, 0)
        }

        fun setAlarmSetTime(time: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_TIME_ID, time)
            editor.apply()
        }
    }
}