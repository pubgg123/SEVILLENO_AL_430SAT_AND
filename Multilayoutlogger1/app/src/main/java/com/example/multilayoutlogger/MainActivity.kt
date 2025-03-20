package com.example.multilayoutlogger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "MainActivityLog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        Log.v(TAG, "Verbose: App started")
        Log.d(TAG, "Debug: Debugging MainActivity")
        Log.i(TAG, "Info: MainActivity Loaded")
        Log.w(TAG, "Warning: Potential issue detected")
        Log.e(TAG, "Error: Example error message")
    }

    fun openLinearActivity(view: View) {
        Intent(this, SecondActivity::class.java).also {
            startActivity(it)
        }
    }

    fun openRelativeActivity(view: View) {
        Intent(this, ThirdActivity::class.java).also {
            startActivity(it)
        }
    }
} 