package me.nlmartian.android.aircolor.ui

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.Toast
import com.pawegio.kandroid.find
import me.nlmartian.android.aircolor.ui.NavigationDrawerFragment
import me.nlmartian.android.aircolor.R
import me.nlmartian.android.aircolor.data.AqiClient
import me.nlmartian.android.aircolor.data.model.PosAqi
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1

public class MainActivity : ActionBarActivity(), NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
//    private var mNavigationDrawerFragment: NavigationDrawerFragment? = null

    /**
     * Used to store the last screen title. For use in [.restoreActionBar].
     */
    private var mTitle: CharSequence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ActionBarActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = find<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

//        mNavigationDrawerFragment = getSupportFragmentManager().findFragmentById(R.id.navigation_drawer) as NavigationDrawerFragment
        val color = getResources().getColor(R.color.light_blue)
        getSupportActionBar().setBackgroundDrawable(ColorDrawable(color))
        getWindow().setStatusBarColor(color)

        // Set up the drawer.
//        mNavigationDrawerFragment!!.setUp(R.id.navigation_drawer, findViewById(R.id.drawer_layout) as DrawerLayout)

        val fragmentManager = getSupportFragmentManager()
        fragmentManager.beginTransaction()
                .replace(R.id.container, CityDetailFragment.newInstance())
                .commit()
    }

    override fun onNavigationDrawerItemSelected(position: Int) {
        // update the main content by replacing fragments
        val fragmentManager = getSupportFragmentManager()
        fragmentManager.beginTransaction()
                .replace(R.id.container, CityDetailFragment.newInstance())
                .commit()
    }

    public fun onSectionAttached(number: Int) {
        when (number) {
            1 -> mTitle = getString(R.string.title_section1)
            2 -> mTitle = getString(R.string.title_section2)
            3 -> mTitle = getString(R.string.title_section3)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super<ActionBarActivity>.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super<ActionBarActivity>.onOptionsItemSelected(item)
    }
}