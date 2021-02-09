package qshn.c11.refresh_rate

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.Toast

private const val MIN_REFRESH_RATE = "min_refresh_rate"
private const val PEAK_REFRESH_RATE = "peak_refresh_rate"

enum class RefreshRate(val refreshRateValue: String) {
    Thirty("30"),
    FiftyNine("59.0"),
    Ninety("90"),
    HundredTwenty("120.0")
}

fun setConfig(context: Context, key: String, value: String) {
    val contentResolver = context.contentResolver
    try {
        val contentValues = ContentValues(2)
        contentValues.put("name", key)
        contentValues.put("value", value)
        contentResolver.insert(Uri.parse("content://settings/system"), contentValues)
    } catch (th: Exception) {
        Toast.makeText(context, "Failed to set value $value", Toast.LENGTH_SHORT).show()
        th.printStackTrace()
    }
}

const val CHANNEL_ID = "boot_channel"

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "开机通知"
        val descriptionText = "通知描述"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun notify(context: Context) {
    val notificationBuilder =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_refresh_black_24dp)
            .setContentTitle("设置成功")
            .setContentText("设置 90Hz 成功")
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .setAutoCancel(true)
    createNotificationChannel(context)
    with(NotificationManagerCompat.from(context)) {
        notify(1, notificationBuilder.build())
    }

}

fun setRefreshRateByContext(context: Context, rate: RefreshRate) {
    setConfig(context, MIN_REFRESH_RATE, rate.refreshRateValue)
    setConfig(context, PEAK_REFRESH_RATE, rate.refreshRateValue)
}

fun set90RefreshRateByContext(context: Context) {
//    setRefreshRateByContext(context, RefreshRate.NinetySix)
    Toast.makeText(context, "90Hz: 正在设置，请稍等", Toast.LENGTH_SHORT).show()
    setRefreshRateByContext(context, RefreshRate.Thirty)
    Toast.makeText(context, "90Hz: 设置成功。", Toast.LENGTH_SHORT).show()
}

fun Context.setRefreshRate(rate: RefreshRate) {
    setRefreshRateByContext(this, rate)
}

fun Context.set90RefreshRate() {
    set90RefreshRateByContext(this)
}