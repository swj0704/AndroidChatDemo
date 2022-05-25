package com.wonjoon.androidchatdemo.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.wonjoon.androidchatdemo.R
import com.wonjoon.androidchatdemo.databinding.FragmentChatSearchBinding
import com.wonjoon.androidchatdemo.viewmodel.ChatSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ChatSearchFragment : Fragment() {

    val binding by lazy {
        FragmentChatSearchBinding.inflate(layoutInflater)
    }

    val viewModel : ChatSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchChatRoom("")

        val textChange: PublishSubject<String> = PublishSubject.create()

        binding.searchTextInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                textChange.onNext(binding.searchTextInput.text.toString().trim())
            }

        })

        textChange
            .debounce(1, TimeUnit.SECONDS)
            .doOnNext{
                viewModel.searchChatRoom(it)
            }
            .subscribe()
    }
}