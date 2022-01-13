package com.jayesh.shadi.repository
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.jayesh.shadi.OConstants
import com.jayesh.shadi.callback.ModelCallbacks
import com.jayesh.shadi.model.Profile
import com.jayesh.shadi.model.Results
import com.jayesh.shadi.retrofit.RetrofitClient
import com.jayesh.shadi.room.AppDatabase
import com.jayesh.shadi.room.ProfileTable
import com.jayesh.shadi.room.ProfileTableDao


public class DataRepository() {
    private val listOfData: MutableLiveData<Results> =
        MutableLiveData<Results>()
    private var dataRepository: DataRepository? = null
    private lateinit var appDatabase:AppDatabase
    private lateinit var profileDao:ProfileTableDao
    private lateinit var listOfDataTable:LiveData<MutableList<ProfileTable>>
    fun getInstance(): DataRepository? {
        if (dataRepository == null) {
            dataRepository = DataRepository()
        }
        return dataRepository
    }

    fun setDatabase(application: Application){
        appDatabase = Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java, OConstants.DB
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        profileDao = appDatabase.profileTableDao()
    }


    fun getDao():ProfileTableDao{
        return profileDao
    }

     fun getData(page:Int,results:Int,callbacks: ModelCallbacks){
         Log.e("online fetch ","page "+page+" result"+results)
        val call = RetrofitClient.getInstance(OConstants.BASE_URL).myApi.getProfiles(results,page)
        call.enqueue(object : Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                Log.e("succcess","Success")
                if(response.body()!=null && response.body()?.results!=null) {
                    updateRoomDB(response.body()?.results!!)
                    callbacks.onSuccess(response.body())
                }
            }
            override fun onFailure(call: Call<Results>, t: Throwable) {
                callbacks.onError(t.message)
            }
        })

    }

    private fun updateRoomDB(results:MutableList<Profile>){
        val listEmail  = profileDao.getProfileIds()
        for (i in results){
            if(listEmail?.contains(i.email) == false) {
                val new_id = profileDao.insert(mapDataforRoom(i))
                if(new_id>0){
                    listEmail?.add(i.email)
                }
            }
        }
    }

    fun mapDataforRoom(result:Profile):ProfileTable{
        val profileTable = ProfileTable()
        profileTable.setEmail(result.email)
        profileTable.setFirst(result.name.title+" "+result.name.first+" "+result.name.last)
        profileTable.setLarge(result.picture.large)
        return profileTable
    }
    fun getDataFromRoom(limit:Int,lastId:Int):LiveData<MutableList<ProfileTable>>{
        Log.e("Fetch room ","Limit "+limit+" lastId "+lastId)
            listOfDataTable =  profileDao.getCategory()!!
        if(listOfDataTable.value!=null) {
            Log.e("Fetch room ", "result " + listOfDataTable.value!!.size!!)

        }else{
            Log.e("Fetch room ", "Blank")
        }
        return listOfDataTable


    }

    fun updateStatus(status:String,email:String){
        profileDao.update(status,email)
    }

}