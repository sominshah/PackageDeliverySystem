package com.product.delivery.system.features.delivery;
import com.product.delivery.system.features.pricing.DeliveryCostCalculator;
import com.product.delivery.system.models.BaseRate;
import com.product.delivery.system.models.Product;
import com.product.delivery.system.models.Vehicle;
import com.product.delivery.system.models.VehicleSpeed;
import com.product.delivery.system.services.ProductService;
import com.product.delivery.system.services.VehicleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
public class DeliverySchedulerTest
{
    private ProductService productService;
    private VehicleService vehicleService;
    @Before
    public void init()
    {
        productService = new ProductService();
        vehicleService = new VehicleService();
        DeliveryCostCalculator.addBaseRate(new BaseRate(new BigDecimal(100)));
        vehicleService.addVehicle(new Vehicle(new VehicleSpeed(new BigDecimal(50)), new BigDecimal(200), "Vehicle-1"));
        vehicleService.addVehicle(new Vehicle(new VehicleSpeed(new BigDecimal(60)), new BigDecimal(150), "Vehicle-2"));
        productService.add(new Product("P1", new BigDecimal("30"), new BigDecimal("50")));
        productService.add(new Product("P2", new BigDecimal("100"), new BigDecimal("40")));
        productService.add(new Product("P3", new BigDecimal("60"), new BigDecimal("20")));
    }

    @Test
    public void testAllProductsAreAssignedDeliveryTimes()
    {
        DeliveryScheduler scheduler = new DeliveryScheduler();
        scheduler.scheduleDeliveries(productService,vehicleService);
        for (Product product : productService.getProducts())
        {
            Assert.assertTrue("Product " +product.getPackageLabel()+ " should be marked scheduled", product.isScheduled());
            Assert.assertNotNull("Product " +product.getPackageLabel()+ " should have delivery time (display)",product.getDeliveryTimeDisplay());
            Assert.assertNotNull("deliveryTimePrecise must be set",product.getDeliveryTimePrecise());
        }
    }

    @Test
    public void testOverweightProductNotScheduled()
    {
        vehicleService = new VehicleService();
        vehicleService.addVehicle(new Vehicle(new VehicleSpeed(new BigDecimal(50)), new BigDecimal(50), "SmallVehicle"));
        Product overweight = new Product("Heavy", new BigDecimal("20"), new BigDecimal("100"));
        productService = new ProductService();
        productService.add(overweight);
        DeliveryScheduler scheduler = new DeliveryScheduler();
        scheduler.scheduleDeliveries(productService, vehicleService);
        Assert.assertFalse("Overweight product should not be scheduled", overweight.isScheduled());
    }


    @Test
    public void testNoVehiclesMeansNoDeliveryTimes()
    {
        vehicleService = new VehicleService();
        DeliveryScheduler scheduler = new DeliveryScheduler();
        scheduler.scheduleDeliveries(productService, vehicleService);
        for (Product product : productService.getProducts())
        {
            Assert.assertFalse(product.getPackageLabel() +" should not be marked scheduled without vehicles",product.isScheduled());
        }
    }


    @Test
    public void testDeliveryTimesAreLogicalBasedOnDistance()
    {
        DeliveryScheduler scheduler = new DeliveryScheduler();
        scheduler.scheduleDeliveries(productService, vehicleService);
        Product p1 = productService.getProducts().stream()
                .filter(p -> "P1".equals(p.getPackageLabel())).findFirst().get();
        Product p2 = productService.getProducts().stream()
                .filter(p -> "P2".equals(p.getPackageLabel())).findFirst().get();
        Assert.assertTrue("P1 should have delivery time <= P2", p1.getDeliveryTimeDisplay().compareTo(p2.getDeliveryTimeDisplay()) <= 0);
    }
}