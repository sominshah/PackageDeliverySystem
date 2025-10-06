package com.product.delivery.system.features.pricing;
import com.product.delivery.system.interfaces.Criteria;
import com.product.delivery.system.models.Product;
import java.math.BigDecimal;
public class DistanceAndWeightCriteria implements Criteria<Product>
{
    private BigDecimal minDistance;
    private BigDecimal maxDistance;
    private BigDecimal minWeight;
    private BigDecimal maxWeight;
    public DistanceAndWeightCriteria(BigDecimal minDistance, BigDecimal maxDistance, BigDecimal minWeight, BigDecimal maxWeight)
    {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }
    public BigDecimal getMinDistance()
    {
        return minDistance;
    }
    public void setMinDistance(BigDecimal minDistance)
    {
        this.minDistance = minDistance;
    }
    public BigDecimal getMaxDistance()
    {
        return maxDistance;
    }
    public void setMaxDistance(BigDecimal maxDistance)
    {
        this.maxDistance = maxDistance;
    }
    public BigDecimal getMinWeight()
    {
        return minWeight;
    }
    public void setMinWeight(BigDecimal minWeight)
    {
        this.minWeight = minWeight;
    }
    public BigDecimal getMaxWeight()
    {
        return maxWeight;
    }
    public void setMaxWeight(BigDecimal maxWeight)
    {
        this.maxWeight = maxWeight;
    }

    public boolean isApplicable(Product p)
    {
        if (p==null) return false;
        BigDecimal d = p.getDistance();
        BigDecimal w = p.getWeight();
        if(w==null || d==null || minDistance ==null || maxDistance ==null || minWeight==null || maxWeight==null)return false;
        if(d.compareTo(minDistance)<0 || d.compareTo(maxDistance)>0) return false;
        if(w.compareTo(minWeight)<0 || w.compareTo(maxWeight)>0) return false;
        return true;
    }


}
