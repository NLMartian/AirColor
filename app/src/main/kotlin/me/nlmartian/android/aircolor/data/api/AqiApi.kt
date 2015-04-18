package me.nlmartian.android.aircolor.data.api

import me.nlmartian.android.aircolor.data.model.PosAqi
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

/**
 * Created by nlmartian on 4/18/15.
 */
trait AqiApi {
    [GET("/pm2_5.json")]
    fun getCityAqi([Query("city")] city: String) : Observable<List<PosAqi>>
}