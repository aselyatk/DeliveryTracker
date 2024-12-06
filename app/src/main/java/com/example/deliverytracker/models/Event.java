package com.example.deliverytracker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class Event implements Serializable {
    public Event(String id,String operationAttribute,String operationPlaceNameTranslated,String operationDateTime,String serviceName,String dataId){
        this.id=id;
        this.DataId=dataId;
        this.operationAttribute=operationAttribute;
        this.operationPlaceNameTranslated=operationPlaceNameTranslated;
        this.operationDateTime=operationDateTime;
        this.serviceName=serviceName;
    }
    @SerializedName("id")
    public String id;
    @SerializedName("operationAttributeTranslated")
    public String operationAttributeTranslated;

    @SerializedName("operationPlaceNameTranslated")
    public String operationPlaceNameTranslated;

    @SerializedName("operationDateTime")
    public String operationDateTime;

    @SerializedName("operationAttribute")
    public String operationAttribute;

    @SerializedName("serviceName")
    public String serviceName;
    public String DataId;
}