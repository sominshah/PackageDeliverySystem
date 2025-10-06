package com.product.delivery.system.models;

import java.math.BigDecimal;
import java.util.UUID;

public class Vehicle
{
    private String id;
    private String vehicleLabel;
    private BigDecimal payloadCapacity;
    private VehicleSpeed vehicleSpeed;
    private BigDecimal availableAt = BigDecimal.ZERO;
    public Vehicle(VehicleSpeed vehicleSpeed,BigDecimal payloadCapacity,String vehicleLabel)
    {
        this.id = UUID.randomUUID().toString();
        this.vehicleSpeed = vehicleSpeed;
        this.payloadCapacity = payloadCapacity;
        this.vehicleLabel = vehicleLabel;
    }


    public String getId()
    {
        return id;
    }
    public String getVehicleLabel()
    {
        return vehicleLabel;
    }
    public void setVehicleLabel(String vehicleLabel)
    {
        this.vehicleLabel = vehicleLabel;
    }
    public BigDecimal getPayloadCapacity()
    {
        return payloadCapacity;
    }
    public void setPayloadCapacity(BigDecimal payloadCapacity)
    {
        this.payloadCapacity = payloadCapacity;
    }
    public VehicleSpeed getVehicleSpeed()
    {
        return vehicleSpeed;
    }
    public void setVehicleSpeed(VehicleSpeed vehicleSpeed)
    {
        this.vehicleSpeed = vehicleSpeed;
    }

    public BigDecimal calculateSpeed()
    {
        return vehicleSpeed.getDistanceInKmPerHour();
    }


    public BigDecimal getAvailableAt()
    {
        return availableAt;
    }

    public void setAvailableAt(BigDecimal availableAt)
    {
        this.availableAt = availableAt;
    }

}
