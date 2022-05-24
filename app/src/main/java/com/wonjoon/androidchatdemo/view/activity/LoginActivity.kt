package com.wonjoon.androidchatdemo.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wonjoon.androidchatdemo.databinding.ActivityLoginBinding
import com.wonjoon.androidchatdemo.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    val viewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.loginNoaccount.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        viewModel.userItemModel.observe(this) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", it.name)
            startActivity(intent)
            finish()
        }
    }
}