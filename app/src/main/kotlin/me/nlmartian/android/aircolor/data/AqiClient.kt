package me.nlmartian.android.aircolor.data

import com.google.gson.Gson
import me.nlmartian.android.aircolor.data.api.AqiApi
import retrofit.RequestInterceptor
import retrofit.RestAdapter
import retrofit.client.OkClient
import retrofit.converter.GsonConverter

/**
 * Created by nlmartian on 4/18/15.
 */
class AqiClient {

    companion object {
        private val API_KEY = "5j1znBVAsnSf5xQyNQyq"

        private val BASE_URL = "http://www.pm25.in/api/querys"

        private val requestInterceptor = object : RequestInterceptor {
            override fun intercept(request: RequestInterceptor.RequestFacade?) {
                request?.addQueryParam("token", API_KEY)
            }
        }

        private val restAdapter = RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(OkClient())
                .setConverter(GsonConverter(Gson()))
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build()

        public val api: AqiApi = restAdapter.create(javaClass<AqiApi>())
    }
}
