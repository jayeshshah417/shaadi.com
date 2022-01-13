package com.jayesh.shadi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jayesh.shadi.callback.ModelCallbacks
import com.jayesh.shadi.model.Results
import com.jayesh.shadi.room.ProfileTable
import com.jayesh.shadi.viewModels.MainViewModel

class MainActivity : AppCompatActivity(),ProfileViewPagerAdapter.ProfileCallbacks ,
    ModelCallbacks {


    private var page:Int = 1
    private lateinit var profileList:MutableList<ProfileTable>
    private lateinit var dataViewModel: MainViewModel
    private lateinit var viewPager2:ViewPager2
    private lateinit var tablayout:TabLayout
    private lateinit var tv_skip: TextView

    private var curr_position:Int = 0
    private var curr_position_new:Int = 10
    private var max_position:Int = 0
    private var firstLoad:Boolean = true
    private var pref:OPreferenceManager? = null
    private lateinit var viewPagerAdapter:ProfileViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        pref = OPreferenceManager(applicationContext)
        curr_position_new = pref!!.getInt("CurrPosition",0)-1
        profileList = ArrayList()
        supportActionBar!!.hide()

        viewPager2 = findViewById(R.id.vp2)

        viewPager2.clipToPadding=false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit =2
        viewPager2.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER

        var compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer(object : ViewPager2.PageTransformer{
            override fun transformPage(page: View, position: Float) {
                var r = 1- Math.abs(position)
                page.scaleY = 0.85f + r  * 0.15f
            }
        })

        viewPager2.setPageTransformer(compositePageTransformer)
        tablayout  = findViewById(R.id.tablayout)



        viewPagerAdapter =
            ProfileViewPagerAdapter(ArrayList(), applicationContext,this)
        viewPager2.adapter = viewPagerAdapter
        viewPagerAdapter.notifyDataSetChanged()
        TabLayoutMediator(tablayout, viewPager2) { tab, position ->

        }.attach()
        dataViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        dataViewModel.setCallBack(this)
        dataViewModel.getDao().getCategory()!!.observe(this,{
            if(profileList.size==0 || (it.get(it.size-1).getEmail() != profileList.get(profileList.size-1).getEmail())) {
                Log.e("previous data ", "" + profileList.size)
                profileList = it
                Log.e("after data ", "" + profileList.size)
                if (it.size == 0) {
                    dataViewModel.getDataOnline(profileList.size / 10, 10)
                }
                viewPagerAdapter.setData(profileList)
                viewPagerAdapter.notifyDataSetChanged()
                if (firstLoad) {
                    viewPager2.setCurrentItem(curr_position_new)
                    firstLoad = false
                    Log.e("the size", profileList.size.toString())

                    tablayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            if(tab?.position == profileList.size-1){
                                page = page+1
                                dataViewModel.getNextData(curr_position_new, 10, profileList)
                            }
                            curr_position = tab!!.position
                            curr_position_new = profileList.get( tab!!.position).getId()!!
                            Log.e("curr id ",curr_position_new.toString())
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }

                    })
                }
            }
        })




    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putInt("CurrPosition", curr_position_new)
        super.onSaveInstanceState(savedInstanceState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        curr_position_new = savedInstanceState.getInt("CurrPosition")
    }

    override fun onDestroy() {
        pref?.putInt("CurrPosition",curr_position_new)
        super.onDestroy()
    }

    override fun onAccept(position: Int) {
        val profile = profileList.get(curr_position)
        profile.setStatus("Accept")
        viewPagerAdapter.replaceData(profile,curr_position)
        viewPagerAdapter.notifyItemChanged(curr_position)
        dataViewModel.updateProfileStatus("Accept",viewPagerAdapter.getData().get(curr_position).getEmail()!!)
        viewPager2.setCurrentItem(curr_position+1)
    }

    override fun onReject(position: Int) {
        val profile = profileList.get(curr_position)
        profile.setStatus("Reject")
        viewPagerAdapter.replaceData(profile,curr_position)
        viewPagerAdapter.notifyItemChanged(curr_position)
        dataViewModel.updateProfileStatus("Reject",viewPagerAdapter.getData().get(curr_position).getEmail()!!)
        viewPager2.setCurrentItem(curr_position+1)
    }

    override fun onSkip(position: Int) {
        viewPager2.setCurrentItem(curr_position+1)
    }

    override fun onSuccess(response: Results?) {

    }

    override fun onError(error: String?) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}