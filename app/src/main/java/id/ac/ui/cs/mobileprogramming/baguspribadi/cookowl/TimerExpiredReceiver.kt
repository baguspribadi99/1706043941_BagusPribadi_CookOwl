package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Util.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        PrefUtil.setTimerState(false, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}
