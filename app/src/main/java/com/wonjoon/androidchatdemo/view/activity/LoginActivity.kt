package com.wonjoon.androidchatdemo.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.wonjoon.androidchatdemo.databinding.ActivityLoginBinding
import com.wonjoon.androidchatdemo.di.Prefs
import com.wonjoon.androidchatdemo.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    val viewModel : LoginViewModel by viewModels()

    @Inject
    lateinit var prefs : Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.loginNoaccount.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        viewModel.userItemModel.observe(this) {
            Toast.makeText(this, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
            prefs.name = it.name
            prefs.pubnubUuid = it.uuid
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}