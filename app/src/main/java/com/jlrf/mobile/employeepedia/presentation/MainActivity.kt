package com.jlrf.mobile.employeepedia.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jlrf.mobile.employeepedia.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}