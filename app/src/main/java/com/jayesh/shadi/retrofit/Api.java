package com.jayesh.shadi.retrofit;

import com.jayesh.shadi.OConstants;
import com.jayesh.shadi.model.Profile;
import com.jayesh.shadi.model.Results;
import com.jayesh.shadi.room.ProfileTable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {


    @GET(OConstants.GET_PROFILE)
    Call<Results> getProfiles(
            @Query("results") int results,@Query("page") int page
    );


}

