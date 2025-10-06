package com.product.delivery.system.services;
import com.product.delivery.system.models.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
public class CouponServiceTest
{
    private CouponService couponService;
    @Before
    public void init()
    {
        couponService = new CouponService();
    }
    @Test
    public void testCouponsPopulatedOnInit() {
        Assert.assertEquals(4, couponService.getCouponCount());
    }

    @Test
    public void testApplyValidCoupon() {
        Product product = new Product();
        product.setWeight(BigDecimal.valueOf(100));
        product.setDistance(BigDecimal.valueOf(100));

        couponService.setIfCouponApplicable("OFR002", product);

        Assert.assertTrue(product.isCouponApplied());
        Assert.assertNotNull(product.getCoupon());
        Assert.assertEquals("OFR002", product.getCoupon().getCouponLabel());
    }


    @Test
    public void testApplyInvalidCriteriaCoupon() {
        Product product = new Product();
        product.setWeight(BigDecimal.valueOf(10));
        product.setDistance(BigDecimal.valueOf(20));
        couponService.setIfCouponApplicable("OFR002", product);
        Assert.assertFalse(product.isCouponApplied());
        Assert.assertNull(product.getCoupon());
    }

    @Test
    public void testApplyNonExistentCoupon() {
        Product product = new Product();
        product.setWeight(BigDecimal.valueOf(100));
        product.setDistance(BigDecimal.valueOf(100));
        couponService.setIfCouponApplicable("INVALID", product);
        Assert.assertFalse(product.isCouponApplied());
        Assert.assertNull(product.getCoupon());
    }

}
