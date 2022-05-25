package com.wonjoon.androidchatdemo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wonjoon.androidchatdemo.R
import com.wonjoon.androidchatdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}