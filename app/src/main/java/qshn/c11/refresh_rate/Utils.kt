package qshn.c11.refresh_rate

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.widget.Toast

private const val MIN_REFRESH_RATE = "min_refresh_rate"
private const val PEAK_REFRESH_RATE = "peak_refresh_rate"

enum class RefreshRate(val refreshRateValue: String) {
    FiftyNine("59.0"),
    NinetySix("96.0"),
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

fun Context.setRefreshRate(rate: RefreshRate) {
    setConfig(this, MIN_REFRESH_RATE, rate.refreshRateValue)
    setConfig(this, PEAK_REFRESH_RATE, rate.refreshRateValue)
}
fun Context.set90RefreshRate() {
    setRefreshRate(RefreshRate.NinetySix)
    Toast.makeText(this, "正在设置第一次，请稍等", Toast.LENGTH_SHORT).show()
    setRefreshRate(RefreshRate.FiftyNine)
    Toast.makeText(this, "设置成功，请等待生效。", Toast.LENGTH_SHORT).show()
}