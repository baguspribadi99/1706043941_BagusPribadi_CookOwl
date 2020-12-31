package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Util.NotificationUtil
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Util.PrefUtil

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
            AppConstant.ACTION_STOP -> {
                MainActivity.removeAlarm(context)
                PrefUtil.setTimerState(false, context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstant.ACTION_START -> {
                val secondsRemaining = 60L
                val wakeUpTime = MainActivity.setAlarm(context, 0, secondsRemaining)
                PrefUtil.setTimerState(true, context)
                PrefUtil.setAlarmSetTime(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}
