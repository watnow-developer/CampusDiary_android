package com.watnow.campusdiary.TimeTable

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.watnow.campusdiary.R
import com.watnow.campusdiary.Utils.BottomNavigationViewHelper

/**
 * Created by saitoushunsuke on 2018/02/12.
 */
class TimeTableActivity : AppCompatActivity() {

    private val TAG: String = "TimeTableActivity"

    private val ACTIVITY_NUM: Int = 2

    private val mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_table_activity)
        Log.d(TAG, "onCreate: starting")

        setupBottomNavigationView()
    }

    /* *
    *  BottomNavigationView setup
    */
    private fun setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView")
        val bottomNavigationViewEx: BottomNavigationViewEx = findViewById(R.id.bottomNavViewBar)
        var bottomNavViewHelper: BottomNavigationViewHelper = BottomNavigationViewHelper()
        bottomNavViewHelper.setupBottomNavigationView(bottomNavigationViewEx)
        bottomNavViewHelper.enableNavigation(mContext, bottomNavigationViewEx)
        val menu: Menu = bottomNavigationViewEx.menu
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true
    }

}