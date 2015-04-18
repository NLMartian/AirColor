package me.nlmartian.android.aircolor.ui

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarActivity
import android.view.*
import android.widget.Toast
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
    private var mNavigationDrawerFragment: NavigationDrawerFragment? = null

    /**
     * Used to store the last screen title. For use in [.restoreActionBar].
     */
    private var mTitle: CharSequence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ActionBarActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNavigationDrawerFragment = getSupportFragmentManager().findFragmentById(R.id.navigation_drawer) as NavigationDrawerFragment
        mTitle = getTitle()

        // Set up the drawer.
        mNavigationDrawerFragment!!.setUp(R.id.navigation_drawer, findViewById(R.id.drawer_layout) as DrawerLayout)
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

    public fun restoreActionBar() {
        val actionBar = getSupportActionBar()
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD)
        actionBar.setDisplayShowTitleEnabled(true)
        actionBar.setTitle(mTitle)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (!mNavigationDrawerFragment!!.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_main, menu)
            restoreActionBar()
            return true
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)



            return rootView
        }

        override fun onAttach(activity: Activity) {
            super.onAttach(activity)
            (activity as MainActivity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER))
        }

        companion object {
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            public fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.setArguments(args)
                return fragment
            }
        }
    }

}