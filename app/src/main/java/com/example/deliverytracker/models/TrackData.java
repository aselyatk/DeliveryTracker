package com.example.deliverytracker.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;


public class TrackData {
    public TrackData(String trackCode,String awaiting,LastPoint lastpoint){
        this.trackCode=trackCode;
        this.lastPoint=lastpoint;
        this.awaitingStatus=awaiting;
    }
    @SerializedName("awaitingStatus")
    public String awaitingStatus;
    @SerializedName("trackCode")
    public String trackCode;

    @SerializedName("itemWeight")
    public String itemWeight;

    @SerializedName("fromCountry")
    public String fromCountry;

    @SerializedName("fromCity")
    public String fromCity;

    @SerializedName("destinationCountry")
    public String destinationCountry;

    @SerializedName("destinationCity")
    public String destinationCity;
    @SerializedName("lastPoint")
    public LastPoint lastPoint;
     @SerializedName("events")
    public ArrayList<Event> events;

}
