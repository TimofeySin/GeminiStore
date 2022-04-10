package ru.timofeysin.geministore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, ru.timofeysin.geministore.MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}