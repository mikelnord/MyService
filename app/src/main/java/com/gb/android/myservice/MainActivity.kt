package com.gb.android.myservice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gb.android.myservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startIntentServiceButton.setOnClickListener{
            SysIntentService.startService(this,10)
        }

        binding.startMyIntentServiceButton.setOnClickListener {
            MySysIntentService.startMyService(this,10)
        }

    }



}