package com.jayesh.shadi.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jayesh.shadi.callback.ModelCallbacks
import com.jayesh.shadi.repository.DataRepository
import com.jayesh.shadi.room.ProfileTable
import com.jayesh.shadi.room.ProfileTableDao


class MainViewModel(application: Application) :
    AndroidViewModel(application) {
    private val repository: DataRepository
    private lateinit var callBack: ModelCallbacks


    public fun getData(page: Int, results: Int, profileList: MutableList<ProfileTable>): LiveData<MutableList<ProfileTable>>{
        Log.e("page ","page: "+page+" results "+results + "  list size "+profileList.size)

        var dataReturned1 = repository.getDataFromRoom(results,page)
        Log.e("fetching online ","")
        var valu = dataReturned1.value
        if((dataReturned1.value?.size==0 || dataReturned1.value==null )&& profileList.size>0){
            Log.e("fetching online ","")
            getDataOnline((profileList.size/results)+1,results)
        }

        Log.e("fetching online ","")
        return dataReturned1
    }
    public fun getNextData(page: Int, results: Int, profileList: MutableList<ProfileTable>){
        getDataOnline(page,results)

    }
    public fun setCallBack(callbacks: ModelCallbacks){
        this.callBack = callbacks
    }

    public fun getDataOnline(page:Int,results:Int){
        Log.e("fetching online 1","")
        var dataReturned = repository.getData(page,results,callBack)
    }

    init {
        repository = DataRepository()
        repository.setDatabase(application)
    }


    fun updateProfileStatus(status:String,email:String){
        repository.updateStatus(status,email)
    }

    fun getDao():ProfileTableDao{
        return repository!!.getDao()
    }
}