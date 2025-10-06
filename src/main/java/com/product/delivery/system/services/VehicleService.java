package com.product.delivery.system.services;
import com.product.delivery.system.models.Vehicle;
import java.util.ArrayList;
import java.util.List;
public class VehicleService
{
    private final List<Vehicle> vehicleQueue = new ArrayList<>();
    public void addVehicle(Vehicle vehicle)
    {
        this.vehicleQueue.add(vehicle);
    }
    public List<Vehicle> getVehicles()
    {
        return new ArrayList<>(this.vehicleQueue);
    }

}
