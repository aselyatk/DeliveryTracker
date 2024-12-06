package com.example.deliverytracker.models;

import com.google.gson.annotations.SerializedName;

public class TrackResponse {
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public TrackData data;
    @SerializedName("message")
    public String message;
}