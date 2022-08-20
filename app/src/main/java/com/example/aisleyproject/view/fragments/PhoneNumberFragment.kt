package com.example.aisleyproject.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aisleyproject.R
import com.example.aisleyproject.databinding.FragmentPhoneNumberBinding
import com.example.aisleyproject.utils.ConstantUtils
import com.example.aisleyproject.utils.Error
import com.example.aisleyproject.utils.Success
import com.example.aisleyproject.viewModel.AisleyViewModel
import retrofit2.Response


class PhoneNumberFragment : Fragment() {
  private lateinit var binding:FragmentPhoneNumberBinding
  private val viewModel by viewModels<AisleyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentPhoneNumberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        initObservers()
    }
    private fun initObservers(){
        viewModel.phoneRegisterData.observe(viewLifecycleOwner){
            when(it){
                is Success<*>->{
                    /*
                    navigate to
                    otp verify screen
                     */
                    Log.d("NAVIHGATE TAG","NAVIGATINGLLLLLLL")
                    val bundle=Bundle()
                    bundle.putString(ConstantUtils.PHONE_NUMBER,"+91${binding.phoneNumberEt.text}")
                    findNavController().navigate(R.id.action_phoneNumberFragment_to_otpFragment,bundle)
                    viewModel.emptyPhoneRegisterLiveData()

                }
                is Error->{

                }
            }
        }
    }
    private fun setUpListeners(){
        binding.apply {
            continueBtn.setOnClickListener {
                val phone=phoneNumberEt.text.toString()
                if(phone.isNotEmpty()&&phone.length==10){
                    viewModel.registerNumber("+91$phone")
                }
                else{
                    Toast.makeText(requireContext(),"Please Enter Valid number",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}