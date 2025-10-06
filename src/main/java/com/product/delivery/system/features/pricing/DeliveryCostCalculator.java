package com.product.delivery.system.features.pricing;
import com.product.delivery.system.interfaces.Rate;
import com.product.delivery.system.models.Product;
import java.math.BigDecimal;
public class DeliveryCostCalculator
{
    private static Rate baseRate;
    private final BigDecimal weightMultiplicand = new BigDecimal(10);
    BigDecimal distanceMultiplicand = new BigDecimal(5);
    public static void addBaseRate(Rate rate)
    {
        baseRate = rate;
    }
    public void calculate(Product product)
    {
        BigDecimal weightCost = product.getWeight().multiply(weightMultiplicand);
        BigDecimal distanceCost = product.getDistance().multiply(distanceMultiplicand);
        BigDecimal totalCost = weightCost.add(distanceCost);
        BigDecimal finalDeliveryCost = baseRate.getRate().add(totalCost);
        BigDecimal discount= new BigDecimal(0);
        if(product.isCouponApplied())
        {
            discount = finalDeliveryCost.multiply(product.getCoupon().getDiscountPercent()).divide(new BigDecimal(100));
            finalDeliveryCost = finalDeliveryCost.subtract(discount);
        }
        product.setDiscount(discount);
        product.setTotalDeliveryCost(finalDeliveryCost);
        product.setDeliveryCost(totalCost);
    }
}
