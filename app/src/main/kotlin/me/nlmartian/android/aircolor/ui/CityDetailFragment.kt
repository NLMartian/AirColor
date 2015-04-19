package me.nlmartian.android.aircolor.ui;

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarActivity
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
import java.util.Date

/**
 * Created by nlmartian on 4/14/15.
 */
public class CityDetailFragment : Fragment() {

    var tvCityName: TextView? = null
    var tvAqi: TextView? = null
    var tvQuality: TextView? = null
    var tvUpdateTime: TextView? = null
    var layoutBg: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_city_detail, container, false)

        tvCityName = view?.find<TextView>(R.id.city_name)
        tvAqi = view?.find<TextView>(R.id.aqi)
        tvQuality = view?.find<TextView>(R.id.quality)
        tvUpdateTime = view?.find<TextView>(R.id.update_time)
        layoutBg = view?.find<View>(R.id.content_bg)

        getAqi()

        return view
    }

    fun getAqi() {
        AqiClient.api?.getCityAqi("021")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Action1<List<PosAqi>> {
                    override fun call(aqis: List<PosAqi>?) {
                        if ((aqis?.isEmpty() as Boolean).not()) {
                            val aqi = aqis?.get(aqis?.size()?.minus(1) as Int)
                            tvCityName?.setText(aqi?.area)
                            tvAqi?.setText(aqi?.aqi.toString())
                            tvQuality?.setText(aqi?.quality)
                            tvUpdateTime?.setText(formatUpdateTime(aqi!!.time_point))
                            val color = getResources().getColor(getColorRes(aqi?.quality))
                            layoutBg?.setBackgroundColor(color)
                            (getActivity() as ActionBarActivity)
                                    .getSupportActionBar()
                                    .setBackgroundDrawable(ColorDrawable(color))
                            (getActivity() as ActionBarActivity).getSupportActionBar().setTitle(null)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                getActivity().getWindow().setStatusBarColor(color)
                            }
                        }
                    }
                }, object: Action1<Throwable> {
                    override fun call(e: Throwable?) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show()
                    }
                })
    }

    fun formatUpdateTime(updateTime: Date): String {
        val offset = System.currentTimeMillis() - updateTime.getTime()
        when(offset) {
            in 0..60000 -> return getString(R.string.update_time_just_now)
            in 60..3600000 -> return getString(R.string.update_minutes_ago, (offset / 60000) as Long)
            else -> return getString(R.string.update_hours_ago, (offset / 3600000) as Long)
        }
    }

    fun getColorRes(quality: String?): Int {
        return when (quality) {
            "优" -> R.color.light_blue
            "良" -> R.color.cyan
            "轻度污染" -> R.color.blue_grey
            "中度污染" -> R.color.grey
            "重度污染" -> R.color.dark_grey
            "严重污染" -> R.color.dark
            else -> R.color.light_blue
        }
    }

    companion object {
        public fun newInstance(): CityDetailFragment {
            val f = CityDetailFragment()
            return f
        }
    }
}
