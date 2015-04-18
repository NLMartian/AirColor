package me.nlmartian.android.aircolor.ui;

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.pawegio.kandroid.find
import me.nlmartian.android.aircolor.R
import me.nlmartian.android.aircolor.data.AqiClient
import me.nlmartian.android.aircolor.data.model.PosAqi
import org.w3c.dom.Text
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1

/**
 * Created by nlmartian on 4/14/15.
 */
public class CityDetailFragment : Fragment() {

    var tvCityName: TextView? = null
    var tvAqi: TextView? = null
    var tvQuality: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_city_detail, container, false)

        tvCityName = view?.find<TextView>(R.id.city_name)
        tvAqi = view?.find<TextView>(R.id.aqi)
        tvQuality = view?.find<TextView>(R.id.quality)

        getAqi()

        return view
    }

    fun getAqi() {
        val aqiScream = AqiClient.api?.getCityAqi("021")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Action1<List<PosAqi>> {
                    override fun call(aqis: List<PosAqi>?) {
                        if ((aqis?.isEmpty() as Boolean).not()) {
                            val aqi = aqis?.get(aqis?.size()?.minus(1) as Int)
                            tvCityName?.setText(aqi?.area)
                            tvAqi?.setText(aqi?.aqi.toString())
                            tvQuality?.setText(aqi?.quality)
                        }
                    }
                }, object: Action1<Throwable> {
                    override fun call(e: Throwable?) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show()
                    }
                })
    }

    companion object {
        public fun newInstance(): CityDetailFragment {
            val f = CityDetailFragment()
            return f
        }
    }
}
