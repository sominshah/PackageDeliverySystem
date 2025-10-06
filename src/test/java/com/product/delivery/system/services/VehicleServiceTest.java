package com.product.delivery.system.services;
import com.product.delivery.system.models.Vehicle;
import com.product.delivery.system.models.VehicleSpeed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.List;

public class VehicleServiceTest
{
    private VehicleService vehicleService;
    @Before
    public void setUp()
    {
        vehicleService = new VehicleService();
    }

    @Test
    public void testAddAndGetVehicles()
    {
        Vehicle v1 = new Vehicle(new VehicleSpeed(new BigDecimal(70)), new BigDecimal("200"), "V1");
        Vehicle v2 = new Vehicle(new VehicleSpeed(new BigDecimal(70)), new BigDecimal("200"), "V2");

        vehicleService.addVehicle(v1);
        vehicleService.addVehicle(v2);
        List<Vehicle> vehicles = vehicleService.getVehicles();
        Assert.assertEquals(2, vehicles.size());
        Assert.assertTrue(vehicles.contains(v1));
        Assert.assertTrue(vehicles.contains(v2));
    }

    @Test
    public void testGetVehiclesReturnsCopy()
    {
        Vehicle v1 = new Vehicle(new VehicleSpeed(new BigDecimal(70)), new BigDecimal("200"), "V1");
        vehicleService.addVehicle(v1);

        List<Vehicle> vehicles =vehicleService.getVehicles();
        vehicles.clear();
        Assert.assertEquals(1,vehicleService.getVehicles().size());
    }

    @Test
    public void testEmptyService()
    {
        List<Vehicle> vehicles =vehicleService.getVehicles();
        Assert.assertNotNull(vehicles);
        Assert.assertTrue(vehicles.isEmpty());
    }
}
