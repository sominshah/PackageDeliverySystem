package com.product.delivery.system.features.pricing;
import com.product.delivery.system.models.BaseRate;
import com.product.delivery.system.models.Coupon;
import com.product.delivery.system.models.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;

public class DeliveryCostCalculatorTest
{
    private DeliveryCostCalculator calculator;
    @Before
    public void setUp()
    {
        calculator = new DeliveryCostCalculator();
        DeliveryCostCalculator.addBaseRate(new BaseRate(new BigDecimal(100)));
    }

    @Test
    public void testCalculateWithoutCoupon()
    {
        Product product = new Product();
        product.setWeight(BigDecimal.valueOf(10));
        product.setDistance(BigDecimal.valueOf(20));
        calculator.calculate(product);
        Assert.assertEquals(BigDecimal.valueOf(200), product.getDeliveryCost());
        Assert.assertEquals(BigDecimal.valueOf(300), product.getTotalDeliveryCost());
        Assert.assertEquals(BigDecimal.ZERO, product.getDiscount());
    }

    @Test
    public void testCalculateWithCouponApplied()
    {
        Product product = new Product();
        product.setWeight(BigDecimal.valueOf(10));
        product.setDistance(BigDecimal.valueOf(20));
        Coupon coupon = new Coupon();
        coupon.setDiscountPercent(BigDecimal.valueOf(10));
        product.setCoupon(coupon);
        product.setCouponApplied(true);
        calculator.calculate(product);

        BigDecimal expectedTotal = BigDecimal.valueOf(300); // base(100) + 200
        BigDecimal expectedDiscount = expectedTotal.multiply(BigDecimal.valueOf(10))
                .divide(BigDecimal.valueOf(100)); // 30
        BigDecimal expectedFinal = expectedTotal.subtract(expectedDiscount); // 270

        Assert.assertEquals(BigDecimal.valueOf(200),product.getDeliveryCost());
        Assert.assertEquals(expectedDiscount,product.getDiscount());
        Assert.assertEquals(expectedFinal,product.getTotalDeliveryCost());
    }

    @Test
    public void testCouponNotAppliedEvenIfCouponPresent()
    {
        Product product = new Product();
        product.setWeight(BigDecimal.valueOf(5));
        product.setDistance(BigDecimal.valueOf(5));
        Coupon coupon = new Coupon();
        coupon.setDiscountPercent(BigDecimal.valueOf(50));
        product.setCoupon(coupon);
        product.setCouponApplied(false);
        calculator.calculate(product);
        Assert.assertEquals(BigDecimal.valueOf(75),product.getDeliveryCost());
        Assert.assertEquals(BigDecimal.ZERO,product.getDiscount());
        Assert.assertEquals(BigDecimal.valueOf(175),product.getTotalDeliveryCost());
    }

    @Test
    public void testZeroWeightAndDistance()
    {
        Product product = new Product();
        product.setWeight(BigDecimal.ZERO);
        product.setDistance(BigDecimal.ZERO);
        calculator.calculate(product);
        Assert.assertEquals(BigDecimal.ZERO,product.getDeliveryCost());
        Assert.assertEquals(BigDecimal.valueOf(100),product.getTotalDeliveryCost());
    }
}

