package com.example.aisleyproject.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aisleyproject.databinding.ViewholderProfileItemBinding
import com.example.aisleyproject.model.response.userData.ProfileX
import jp.wasabeef.glide.transformations.BlurTransformation

class ProfileItemAdapter:RecyclerView.Adapter<ProfileItemAdapter.ProfileItemViewHolder>() {
   private val list=ArrayList<ProfileX>()
    fun addData(list:List<ProfileX>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
    inner class  ProfileItemViewHolder(val binding:ViewholderProfileItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItemViewHolder {
        return ProfileItemViewHolder(ViewholderProfileItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProfileItemViewHolder, position: Int) {
        holder.binding.apply {
            val data=list[position]
            Glide.with(userImage).load(data.avatar).apply(RequestOptions.bitmapTransform(BlurTransformation(25,3)))
                .into(userImage)
            name.text=data.first_name
        }
    }

    override fun getItemCount()=list.size
}