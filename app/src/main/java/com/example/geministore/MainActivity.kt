package com.example.geministore

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.geministore.databinding.ActivityMainBinding
import com.example.geministore.services.urovo.UrovoConnectionService
import com.example.geministore.ui.BaseFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

   // private var targetFragmentOrder : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_gallery, R.id.nav_order), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val intent = Intent(applicationContext,UrovoConnectionService ::class.java)
       // ContextCompat.startForegroundService(this,intent)
        val serviceConnection = object : ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                Log.d("BlueT", "MainActivity onServiceConnected");
            }
            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.d("BlueT", "MainActivity onServiceConnected");
            }
        }
    this.bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        supportFragmentManager.let{ itMain ->
            itMain.fragments.last().childFragmentManager.let{
                val currentFragment : BaseFragment = it.fragments.last() as BaseFragment
                  currentFragment.keyDown(keyCode,event)
                return super.onKeyDown(keyCode, event)
            }
        }
    }

}