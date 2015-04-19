package me.nlmartian.android.aircolor.data.model

import java.util.Date

/**
 * Created by nlmartian on 4/18/15.
 */
data class PosAqi(
        var aqi: Int,
        var area: String,
        var pm2_5: Int,
        var pm2_5_24h: Int,
        var position_name: String,
        var primary_pollutant: String,
        var quality: String,
        var station_code: String,
        var time_point: Date
)
