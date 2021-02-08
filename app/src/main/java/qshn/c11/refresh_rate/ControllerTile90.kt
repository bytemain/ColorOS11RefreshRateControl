package qshn.c11.refresh_rate

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

@SuppressLint("NewApi")
class ControllerTile90 : TileService() {
    override fun onStartListening() {
        super.onStartListening()
        val tile = qsTile
        tile.label = getString(R.string.set_90hz)
        tile.state = Tile.STATE_INACTIVE
        tile.icon = Icon.createWithResource(this, R.drawable.ic_refresh_black_24dp)
        tile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        set90RefreshRate()
    }

}
