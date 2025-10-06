package com.product.delivery.system;
import com.product.delivery.system.features.delivery.DeliveryScheduler;
import com.product.delivery.system.features.pricing.DeliveryCostCalculator;
import com.product.delivery.system.interfaces.Rate;
import com.product.delivery.system.models.BaseRate;
import com.product.delivery.system.models.Product;
import com.product.delivery.system.models.Vehicle;
import com.product.delivery.system.models.VehicleSpeed;
import com.product.delivery.system.services.CouponService;
import com.product.delivery.system.services.ProductService;
import com.product.delivery.system.services.VehicleService;
import java.math.BigDecimal;
public class ProductDeliverySystem
{
    private static final DeliveryScheduler deliveryScheduler;
    private static Rate baseRate;
    private static final DeliveryCostCalculator deliveryCostCalculator;
    private static final ProductDeliverySystem packageDeliverySystem;
    private static final CouponService couponService;
    private static final VehicleService vehicleService;
    private static final ProductService productService;
    static
    {
        packageDeliverySystem = new ProductDeliverySystem();
        deliveryCostCalculator = new DeliveryCostCalculator();
        deliveryScheduler = new DeliveryScheduler();
        couponService = new CouponService();
        vehicleService = new VehicleService();
        productService = new ProductService();
    }
    private ProductDeliverySystem()
    {

    }
    public static ProductDeliverySystem getInstance(BigDecimal baseRate)
    {
        ProductDeliverySystem.baseRate = new BaseRate(baseRate);
        DeliveryCostCalculator.addBaseRate(ProductDeliverySystem.baseRate);
        return packageDeliverySystem;
    }
    public static void addVehicles(int numberOfVehicles,int vehicleMaxSpeed, int maxCarriableWeight)
    {
        Vehicle vehicle=null;
        for(int i=0;i<numberOfVehicles;i++)
        {
            vehicle = new Vehicle(new VehicleSpeed(new BigDecimal(vehicleMaxSpeed)), new BigDecimal(maxCarriableWeight), "Vehicle-"+(i+1));
            vehicleService.addVehicle(vehicle);
        }
    }

    public void addProduct(String couponId,Product product)
    {
        couponService.setIfCouponApplicable(couponId,product);
        productService.add(product);
    }
    public void printDeliveryCost()
    {
        productService.printProducts(deliveryCostCalculator);
    }
    public void printDeliveryCostWithEstimatedTime()
    {
        deliveryScheduler.scheduleDeliveries(productService,vehicleService);
        productService.printProductsWithEstimatedTime(deliveryCostCalculator);
    }
}
