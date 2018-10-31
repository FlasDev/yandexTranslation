package com.example.yandextranslator.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.yandextranslator.R
import com.example.yandextranslator.ui.base.BaseActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.main_nav_controller)

        setSupportActionBar(main_bottom_app_bar)
        setupActionBar()
        setupNavigationMenu()
    }

    override fun onResume() {
        super.onResume()


    }

    private fun setupActionBar(){
        NavigationUI.setupActionBarWithNavController(this, navController,main_drawer_layout)
    }
    private fun setupNavigationMenu(){
        main_nav_view?.let {navigationView->
            NavigationUI.setupWithNavController(navigationView, navController)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        NavigationUI.onNavDestinationSelected(item, navController)
        when(item.itemId){
            android.R.id.home->{openBottomAppBar()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(main_drawer_layout,
                Navigation.findNavController(this, R.id.main_nav_controller))
    }

    override fun onBackPressed() {
        openBottomAppBar()
        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)){
            main_drawer_layout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }

    private fun openBottomAppBar(){
        main_fab.hide(object: FloatingActionButton.OnVisibilityChangedListener(){
            override fun onHidden(fab: FloatingActionButton?) {
                super.onHidden(fab)
                main_bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab?.setImageDrawable(resources.getDrawable(R.drawable.ic_g_translate, null))
                fab?.show()
            }
        })
    }
}
