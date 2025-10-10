package com.product.delivery.system.models;
import com.product.delivery.system.interfaces.Criteria;
import java.math.BigDecimal;
import java.util.UUID;

public class Coupon
{
    String couponId;
    String couponLabel;
    Criteria<Product> criteria;
    BigDecimal discountPercentage;
    public Coupon()
    {

    }
    public Coupon(String couponLabel, BigDecimal discountPercentage)
    {
        this.couponId = UUID.randomUUID().toString();
        this.couponLabel = couponLabel;
        this.discountPercentage = discountPercentage;
    }
    public String getCouponId()
    {
        return couponId;
    }
    public String getCouponLabel()
    {
        return couponLabel;
    }
    public void setCouponLabel(String couponLabel)
    {
        this.couponLabel = couponLabel;
    }
    public Criteria<Product> getCriteria()
    {
        return criteria;
    }
    public void setCriteria(Criteria<Product> criteria)
    {
        this.criteria = criteria;
    }
    public BigDecimal getDiscountPercent()
    {
        return discountPercentage;
    }
    public void setDiscountPercent(BigDecimal discountPercent)
    {
        this.discountPercentage= discountPercent;
    }
}
