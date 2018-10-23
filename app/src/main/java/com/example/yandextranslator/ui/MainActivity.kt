package com.example.yandextranslator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.yandextranslator.R
import com.example.yandextranslator.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.main_nav_controller)

        setSupportActionBar(main_toolbar)
        setupActionBar()
        setupNavigationMenu()
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
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(main_drawer_layout,
                Navigation.findNavController(this, R.id.main_nav_controller))
    }

    override fun onBackPressed() {
        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)){
            main_drawer_layout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }
}
