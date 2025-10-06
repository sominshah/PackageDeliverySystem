package com.product.delivery.system.models;
import java.math.BigDecimal;
public class VehicleSpeed
{
    private BigDecimal distanceInKmPerHour;

    public VehicleSpeed(BigDecimal distanceInKmPerHour) {
        this.distanceInKmPerHour = distanceInKmPerHour;
    }
    public BigDecimal getDistanceInKmPerHour()
    {
        return distanceInKmPerHour;
    }
    public void setDistanceInKmPerHour(BigDecimal distanceInKmPerHour) {
        this.distanceInKmPerHour = distanceInKmPerHour;
    }

}
