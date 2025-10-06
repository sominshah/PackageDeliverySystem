package com.product.delivery.system.services;
import com.product.delivery.system.features.pricing.DistanceAndWeightCriteria;
import com.product.delivery.system.models.Coupon;
import com.product.delivery.system.models.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class CouponService
{
    final List<Coupon> coupons = new ArrayList<>();
    public void addCoupon(Coupon coupon)
    {
        this.coupons.add(coupon);
    }
    public CouponService()
    {
        populateCoupons();
    }
    public int getCouponCount()
    {
        return coupons.size();
    }

//    public Coupon getBestCoupon(Product p)
//    {
//        return coupons.stream()
//                .filter(c -> c.getCriteria().isApplicable(p))
//                .max(Comparator.comparing(c -> c.getDiscountPercent()))
//                .orElse(null);
//    }

    public void setIfCouponApplicable(String couponLabel,Product product)
    {
        if (product == null || couponLabel == null) return;
        Coupon coupon = coupons.stream().filter( c -> c.getCouponLabel().equals(couponLabel)).findFirst().orElse(null);
        if (coupon != null && coupon.getCriteria().isApplicable(product))
        {
            product.setCoupon(coupon);
            product.setCouponApplied(true);
        } else {
            product.setCoupon(null);
            product.setCouponApplied(false);
        }
    }

    public void populateCoupons()
    {
        //Feeding hard coded data. This code should be replaced with the code that populates
        // coupons from database

        Coupon of1 = new Coupon();
        of1.setCouponLabel("OFR003");
        of1.setCriteria(new DistanceAndWeightCriteria(new BigDecimal(50),new BigDecimal(250),new BigDecimal(10),new BigDecimal(150)));
        of1.setDiscountPercent(new BigDecimal(5));
        coupons.add(of1);

        of1 = new Coupon();
        of1.setCouponLabel("OFR002");
        of1.setCriteria(new DistanceAndWeightCriteria(new BigDecimal(50),new BigDecimal(150),new BigDecimal(100),new BigDecimal(250)));
        of1.setDiscountPercent(new BigDecimal(7));

        coupons.add(of1);

        of1 = new Coupon();
        of1.setCouponLabel("OFR001");
        //since there is no upper limit was decided.
        of1.setCriteria(new DistanceAndWeightCriteria(new BigDecimal(200),new BigDecimal(Integer.MAX_VALUE),new BigDecimal(70),new BigDecimal(200)));
        of1.setDiscountPercent(new BigDecimal(10));
        coupons.add(of1);
        //System.out.println("Coupons got populated: "+coupons.size());


        of1 = new Coupon();
        of1.setCouponLabel("OFFR002");
        of1.setCriteria(new DistanceAndWeightCriteria(new BigDecimal(50),new BigDecimal(150),new BigDecimal(100),new BigDecimal(250)));
        of1.setDiscountPercent(new BigDecimal(7));
        coupons.add(of1);
    }
}
