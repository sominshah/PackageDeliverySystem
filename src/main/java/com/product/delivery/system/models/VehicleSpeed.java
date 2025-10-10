package com.product.delivery.system.models;
import java.math.BigDecimal;
public class VehicleSpeed
{
    final private BigDecimal distanceInKmPerHour;
    public VehicleSpeed(BigDecimal distanceInKmPerHour)
    {
        this.distanceInKmPerHour = distanceInKmPerHour;
    }
    public BigDecimal getDistanceInKmPerHour()
    {
        return distanceInKmPerHour;
    }
}
