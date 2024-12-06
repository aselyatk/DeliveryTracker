package com.example.deliverytracker.Services;
import android.util.Log;

import com.example.deliverytracker.models.TrackResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingRepository {
    private final TrackingApiService apiService;

    public TrackingRepository() {
        this.apiService = RetrofitClient.getInstance().create(TrackingApiService.class);
    }

    public void fetchTrackingData(String trackingCode, final TrackingDataCallback callback) {
        Call<TrackResponse> call = apiService.fetchTrackingData(
                "529977fd538dc479d8e3123df390acd5", // API key
                "deliverytrackername.com",           // Domain
                true,                                // Pretty print
                trackingCode                         // Tracking code
        );

        call.enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TrackResponse trackResponse = response.body();
                    trackResponse.data.events.forEach(e->{
                        e.DataId=trackResponse.data.trackCode;
                    });
                    callback.onSuccess(trackResponse);
                } else {
                    callback.onError(new Exception("Server error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public interface TrackingDataCallback {
        void onSuccess(TrackResponse trackResponse);
        void onError(Throwable t);
    }
}
