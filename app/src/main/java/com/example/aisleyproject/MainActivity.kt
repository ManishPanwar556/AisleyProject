package com.example.aisleyproject

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.aisleyproject.viewModel.AisleyViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<AisleyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changestatusBarIconColor()
    }

    private fun changestatusBarIconColor(){
       if(Build.VERSION.SDK_INT>=23){
           val decor=this.window.decorView
           if(decor.systemUiVisibility!=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR){
               decor.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
           }
           else{
               decor.systemUiVisibility=0
           }
        }
    }
}