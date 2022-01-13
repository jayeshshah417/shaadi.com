package com.jayesh.shadi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.jayesh.shadi.room.ProfileTable
import com.squareup.picasso.Picasso

internal class ProfileViewPagerAdapter(private var imagesList: MutableList<ProfileTable>, private var context:Context, private var profileCallBack:ProfileCallbacks):
    RecyclerView.Adapter<ProfileViewPagerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var iv_dashboard: ImageView = view.findViewById(R.id.iv)
        var tv_title: TextView = view.findViewById(R.id.tv_title)
        var tv_sub_title: TextView = view.findViewById(R.id.tv_subtitle)
         var bt_next: Button = view.findViewById(R.id.bt_next)
         var bt_prev: Button= view.findViewById(R.id.bt_prev)
        var tv_skip: TextView= view.findViewById(R.id.tv_skip)
        var tv_accept: ImageView= view.findViewById(R.id.accept)
        var tv_reject: ImageView= view.findViewById(R.id.reject)

    }

    interface ProfileCallbacks {
        fun onAccept(position: Int)
        fun onReject(position: Int)
        fun onSkip(position: Int)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewpager_introslider_item_layout, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categ = imagesList[position]

        holder.tv_title.text = categ.getEmail()
        holder.tv_sub_title.text = categ.getFirst()
        var image_url = categ.getLarge()
        Picasso.Builder(context).build()?.load(image_url)?.into(holder.iv_dashboard)

        holder.bt_prev.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                profileCallBack.onReject(holder.adapterPosition)
            }

        })
        holder.bt_next.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                profileCallBack.onAccept(holder.adapterPosition)
            }

        })
        holder.tv_skip.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                profileCallBack.onSkip(holder.adapterPosition)
            }

        })

        if(categ.getStatus()!=null){
            if(categ.getStatus().equals("Accept")){
                holder.tv_accept.visibility = View.VISIBLE
                holder.tv_reject.visibility = View.GONE
            }else{
                holder.tv_reject.visibility = View.VISIBLE
                holder.tv_accept.visibility = View.GONE
            }
            holder.bt_prev.visibility = View.GONE
            holder.bt_next.visibility = View.GONE
            holder.tv_skip.visibility = View.GONE
        }else{
            holder.bt_prev.visibility = View.VISIBLE
            holder.bt_next.visibility = View.VISIBLE
            holder.tv_skip.visibility = View.VISIBLE
            holder.tv_accept.visibility = View.GONE
            holder.tv_reject.visibility = View.GONE
        }

    }
    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun setData(categorylist:MutableList<ProfileTable>){
        this.imagesList = categorylist
    }

    fun replaceData(profile:ProfileTable,position: Int){
        this.imagesList.removeAt(position)
        this.imagesList.add(position,profile  )
    }
    fun getData():MutableList<ProfileTable>{
        return this.imagesList
    }
}