package com.example.geministore

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.geministore.databinding.ActivityMainBinding
import com.example.geministore.services.urovo.UrovoConnectionService
import com.google.android.material.navigation.NavigationView



class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_login, R.id.nav_order_list, R.id.nav_settings, R.id.nav_order), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val intentUrovo = Intent(applicationContext,UrovoConnectionService ::class.java)
        val serviceConnection = object : ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                Log.d("BlueT", "MainActivity onServiceConnected");
            }
            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.d("BlueT", "MainActivity onServiceConnected");
            }
        }
         this.bindService(intentUrovo,serviceConnection, Context.BIND_AUTO_CREATE)
    }
}