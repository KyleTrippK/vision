package com.moringaschool.movieworld.network;

import com.moringaschool.movieworld.models.VisionBusiness;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieWorldApi {
    @GET("search/movie")
    Call<VisionBusiness> getMovies(
            @Query("query") String query,
            @Query("api_key") String api_key
    );
}
