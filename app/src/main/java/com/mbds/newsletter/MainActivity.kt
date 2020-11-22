package com.mbds.newsletter

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.mbds.newsletter.database.AppDatabase
import com.mbds.newsletter.fragments.AboutUsFragment
import com.mbds.newsletter.fragments.ArticlesFragment
import com.mbds.newsletter.fragments.HomeFragment
import com.mbds.newsletter.utils.Endpoints
import java.nio.file.Files.delete

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        changeFragment(HomeFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v("MainActivityMenu", "Item clicked from menu : " + item.title)
        when (item.title) {
            getString(R.string.favorites) -> changeFragment(ArticlesFragment.newInstance("","","", Endpoints.FAVORITES))
            getString(R.string.about_us) -> changeFragment(AboutUsFragment())
        }

        return true
    }
}

fun MainActivity.changeFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragment_container, fragment)
        addToBackStack(null)
    }.commit()
}