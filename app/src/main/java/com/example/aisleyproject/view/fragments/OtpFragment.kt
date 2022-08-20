package com.example.aisleyproject.view.fragments

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aisleyproject.R
import com.example.aisleyproject.databinding.FragmentOtpBinding
import com.example.aisleyproject.model.response.VerifyPhoneResponse
import com.example.aisleyproject.utils.ApiResponse
import com.example.aisleyproject.utils.ConstantUtils
import com.example.aisleyproject.utils.Error
import com.example.aisleyproject.utils.Success
import com.example.aisleyproject.viewModel.AisleyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class OtpFragment : Fragment() {
     private val viewModel by viewModels<AisleyViewModel>()
    private lateinit var binding: FragmentOtpBinding
    private  var phone:String?=null

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentOtpBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(ConstantUtils.PHONE_NUMBER)?.let{
            binding.phoneNumber.text=it
            phone=it
            viewModel.startTimer()
            setUpListener()
            initObservers()
        }
    }

    private fun setUpListener(){
        binding.apply {
            continueBtn.setOnClickListener {
                val otp=otpEt.text.toString()
                if(otp.isNotEmpty()&&otp.length==4){
                    if(phone!=null)
                    viewModel.verifyNumber(phone!!, otp)
                    else
                     Toast.makeText(requireContext(),"Phone is invalid",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireContext(),"Please Enter Valid Otp",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initObservers(){
        viewModel.phoneVerifiedData.observe(viewLifecycleOwner){
            when(it){
                is Success<*>-> {
                    if (it.data != null) {
                        val data = it.data as VerifyPhoneResponse
                        if(data.token!=null&&data.token.isNotEmpty()) {
                            val bundle=Bundle()
                            bundle.putString(ConstantUtils.TOKEN,data.token)
                            findNavController().navigate(R.id.action_otpFragment_to_dashBoardFragment,bundle)
                        }
                        else{
                            Toast.makeText(requireContext(),"Otp is incorrect",Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                }
                is Error->{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.timer.observe(viewLifecycleOwner){
            binding.timerText.text=it
        }
    }


}