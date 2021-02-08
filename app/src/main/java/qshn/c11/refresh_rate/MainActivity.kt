package qshn.c11.refresh_rate

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import jp.wasabeef.takt.Seat
import jp.wasabeef.takt.Takt
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        set90.setOnClickListener { set90RefreshRate() }
        goSetting.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setClassName(
                    "com.android.settings",
                    "com.android.settings.Settings\$DisplaySettingsActivity"
                )
                flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            }
            startActivity(intent)
        }
        goDev.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            startActivity(intent)
        }
        Takt.stock(application)
            .seat(Seat.TOP_RIGHT)
            .interval(500)
            .color(Color.BLACK)
            .size(16f)
            .alpha(0.7f)
    }

    override fun onPause() {
        super.onPause()
        Takt.finish()
    }
}
