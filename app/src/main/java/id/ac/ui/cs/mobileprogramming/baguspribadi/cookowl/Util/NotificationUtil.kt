package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.Util

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.AppConstant
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.MainActivity
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.R
import id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl.TimerNotificationActionReceiver
import java.text.SimpleDateFormat
import java.util.*

class NotificationUtil {
    companion object{
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "Cook Owl's Timer"
        private const val TIMER_ID = 0

        fun showTimerExpired(context: Context){
            val startIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            startIntent.action = AppConstant.ACTION_START

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer Expired")
                .setContentText("Your meal is ready!")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)
            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun showTimerRunning(context: Context, wakeUpTime: Long){
            val stoptIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            stoptIntent.action = AppConstant.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(context, 0, stoptIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val df = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer is Running")
                .setContentText("End at ${df.format(Date(wakeUpTime))}")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .setOngoing(true)
                .addAction(R.drawable.ic_stop, "Stop", stopPendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)
            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun hideTimerNotification(context: Context){
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(TIMER_ID)
        }

        private fun getBasicNotificationBuilder(context: Context, channelId: String, sound: Boolean): NotificationCompat.Builder {
            val nBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_timer)
                .setAutoCancel(true)
                .setDefaults(0)
            if(sound){
                val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                nBuilder.setSound(notificationSound)
            }
            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent{
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        @TargetApi(24)
        private fun NotificationManager.createNotificationChannel(channelID: String, channelName: String, sound: Boolean){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelImportance = if(sound) NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW

                val nChannel = NotificationChannel(channelID, channelName, channelImportance)
                nChannel.enableLights(true)
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)
            }
        }
    }
}