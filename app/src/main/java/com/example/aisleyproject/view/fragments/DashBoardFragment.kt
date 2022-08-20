package com.example.aisleyproject.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.aisleyproject.R
import com.example.aisleyproject.databinding.FragmentDashBoardBinding
import com.example.aisleyproject.model.response.userData.UserDataResponse
import com.example.aisleyproject.utils.ApiResponse
import com.example.aisleyproject.utils.ConstantUtils
import com.example.aisleyproject.utils.Error
import com.example.aisleyproject.utils.Success
import com.example.aisleyproject.view.adapters.ProfileItemAdapter
import com.example.aisleyproject.viewModel.AisleyViewModel


class DashBoardFragment : Fragment() {
    private val viewModel by viewModels<AisleyViewModel>()
    private var token: String? = null
    private val sharedPreference by lazy {
        requireContext().getSharedPreferences(ConstantUtils.SHAREDPREFNAME, Context.MODE_PRIVATE)
    }

    private lateinit var adapter: ProfileItemAdapter
    private lateinit var binding: FragmentDashBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(ConstantUtils.TOKEN)?.let {
            token=it
            with (sharedPreference.edit()) {
                putString(ConstantUtils.TOKEN, it)
                apply()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (token != null) {
            setUpRecyclerView()
            viewModel.getUserData(token!!)
            initObservers()
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun initObservers() {
        viewModel.userData.observe(viewLifecycleOwner) {
            when (it) {
                is Success<*> -> {
                    if (it.data != null)
                        setUpUi(it.data as UserDataResponse)
                    else
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                            .show()
                }
                is Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpUi(data: UserDataResponse) {
        binding.apply {
            progressBar.visibility = View.INVISIBLE
            mainLayout.visibility = View.VISIBLE
            val profile = data.invites.profiles[0]
            Glide.with(userImage).load(profile.photos[0].photo).into(userImage)
            adapter.addData(data.likes.profiles)
            binding.name.text="${profile.general_information.first_name}, ${profile.general_information.age}"
        }
    }


    private fun setUpRecyclerView(){
        binding.apply {
            adapter= ProfileItemAdapter()
            likeRev.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            likeRev.adapter=adapter
        }
    }

}