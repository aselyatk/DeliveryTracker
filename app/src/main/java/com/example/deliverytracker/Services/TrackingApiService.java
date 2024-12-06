package com.example.deliverytracker.Services;

import com.example.deliverytracker.models.TrackResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrackingApiService {
    @GET("tracking.json.php")
    Call<TrackResponse> fetchTrackingData(
            @Query("apiKey") String apiKey,
            @Query("domain") String domain,
            @Query("pretty") boolean pretty,
            @Query("code") String trackingCode
    );
}
