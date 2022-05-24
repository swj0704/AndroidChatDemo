package com.wonjoon.androidchatdemo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.wonjoon.androidchatdemo.R
import com.wonjoon.androidchatdemo.databinding.ActivitySignUpBinding
import com.wonjoon.androidchatdemo.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    val binding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    val viewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.loginEvent.observe(this) {
            finish()
        }

        binding.haveAccount.setOnClickListener {
            finish()
        }
    }
}