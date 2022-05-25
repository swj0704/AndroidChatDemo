package com.wonjoon.androidchatdemo.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wonjoon.androidchatdemo.R
import com.wonjoon.androidchatdemo.databinding.FragmentChatListBinding
import com.wonjoon.androidchatdemo.di.Prefs
import com.wonjoon.androidchatdemo.view.activity.LoginActivity
import com.wonjoon.androidchatdemo.viewmodel.ChatListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ChatListFragment : Fragment() {

    val binding : FragmentChatListBinding by lazy{
        FragmentChatListBinding.inflate(layoutInflater)
    }

    val viewModel : ChatListViewModel by viewModels()

    @Inject
    lateinit var prefs: Prefs

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

        viewModel.getChatList()

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_chat_list_to_chat_search)
        }

        binding.logout.setOnClickListener {
            prefs.name = ""
            prefs.pubnubUuid = UUID.randomUUID().toString()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finishAffinity()
        }
    }
}