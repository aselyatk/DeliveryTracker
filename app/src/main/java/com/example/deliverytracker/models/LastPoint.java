package com.example.deliverytracker.models;

import com.google.gson.annotations.SerializedName;

public class LastPoint {
    public LastPoint(String operationAttribute,String operationPlaceName,String operationDateTime,String serviceName){
        this.serviceName=serviceName;
        this.operationAttribute=operationAttribute;
        this.operationDateTime=operationDateTime;
        this.operationPlaceName=operationPlaceName;
    }
    @SerializedName("operationDateTime")
    public String operationDateTime;
    @SerializedName("operationAttribute")
    public String operationAttribute;
    @SerializedName("serviceName")
    public String serviceName;
    @SerializedName("operationPlaceName")
    public String operationPlaceName;
}
