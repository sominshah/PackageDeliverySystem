package com.product.delivery.system.models;
import java.math.BigDecimal;
import java.util.UUID;
public class Product
{
    private final String packageId;
    private String packageLabel;
    private BigDecimal distance;
    private BigDecimal weight;
    private boolean isCouponApplied;
    private Coupon coupon;
    private BigDecimal deliveryTimePrecise = BigDecimal.ZERO;
    private BigDecimal deliveryTimeDisplay = BigDecimal.ZERO;
    private BigDecimal deliveryCost = BigDecimal.ZERO;
    private BigDecimal discount = BigDecimal.ZERO;
    private BigDecimal totalDeliveryCost = BigDecimal.ZERO;
    private boolean scheduled = false;


    public Product( String packageLabel, BigDecimal distance, BigDecimal weight)
    {
        this.packageId = UUID.randomUUID().toString();
        this.packageLabel = packageLabel;
        this.distance = distance;
        this.weight = weight;
    }
    public Product()
    {
        this.packageId = UUID.randomUUID().toString();
    }


    public boolean isScheduled()
    {
        return scheduled;
    }
    public void setScheduled(boolean scheduled)
    {
        this.scheduled = scheduled;
    }

    public boolean isCouponApplied()
    {
        return isCouponApplied;
    }

    public void setCouponApplied(boolean isCouponApplied) {
        this.isCouponApplied = isCouponApplied;
    }


    public BigDecimal getDistance() {
        return distance;
    }

    public Coupon getCoupon()
    {
        return coupon;
    }
    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
    public void setDistance(BigDecimal distance) throws IllegalArgumentException
    {
        if (distance != null && distance.signum() < 0) throw new IllegalArgumentException("Distance cannot be negative");
        this.distance = distance;
    }
    public BigDecimal getWeight() {
        return weight;
    }
    public void setWeight(BigDecimal weight) throws IllegalArgumentException
    {
        if (weight != null && weight.signum() < 0) throw new IllegalArgumentException("Weight cannot be negative");
        this.weight = weight;
    }
    public String getId() {
        return packageId;
    }
    @Override
    public int hashCode()
    {
        return (packageId != null ? packageId.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product other = (Product) obj;
        return packageId != null && packageId.equals(other.packageId);
    }
    public String getPackageLabel() {
        return packageLabel;
    }
    public void setPackageLabel(String packageLabel) {
        this.packageLabel = packageLabel;
    }
    public void setDeliveryTimeDisplay(BigDecimal dtDisplay) {
        this.deliveryTimeDisplay = dtDisplay;
    }
    public String getDeliveryTimeDisplay() {
        return deliveryTimeDisplay.toPlainString();
    }
    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }
    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }
    public BigDecimal getDiscount() {
        return discount;
    }
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    public BigDecimal getTotalDeliveryCost() {
    return  totalDeliveryCost;
    }
    public void setTotalDeliveryCost(BigDecimal totalDeliveryCost) {
        this.totalDeliveryCost = totalDeliveryCost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product [");
        sb.append("packageId=").append(packageId).append(", ");
        sb.append("packageLabel=").append(packageLabel).append(", ");
        sb.append("distance=").append(distance != null ? distance : "N/A").append(", ");
        sb.append("weight=").append(weight != null ? weight : "N/A").append(", ");
        sb.append("isCouponApplied=").append(isCouponApplied).append(", ");
        sb.append("coupon=").append(coupon != null ? coupon.getCouponId() + " (" + coupon.getDiscountPercent().multiply(new BigDecimal("100")) + "%)" : "None");
        sb.append("]");
        return sb.toString();
    }
}
